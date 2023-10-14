package com.homecode.service;

import com.homecode.model.UserCredential;
import com.homecode.model.model.UserCredentialDTO;
import com.homecode.repository.UserCredentialRepository;
import com.homecode.commons.exception.CustomAlreadyExistException;
import com.homecode.commons.exception.CustomDatabaseOperationException;
import com.homecode.commons.exception.CustomIllegalStateException;
import com.homecode.commons.exception.CustomValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public ResponseEntity<UserCredentialDTO> saveUser(UserCredentialDTO credential) {
        if (!credential.getPassword().equals(credential.getRepeatPassword())) {
            throw new CustomIllegalStateException("Password and repeat password are not the same",
                    "USER_PASSWORDS_NOT_MATCH");
        }
        if (userExist(credential)) {
            throw new CustomAlreadyExistException("User already exist",
                    "USER_EXIST");
        }

        try {
            UserCredential user = mapToUser(credential);
            this.userCredentialRepository.save(user);
            return new ResponseEntity<>(mapToDto(user), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CustomDatabaseOperationException("An error occurred while creating user"
                    , "DATABASE_OPERATION_EXCEPTION");
        }
    }

    private UserCredentialDTO mapToDto(UserCredential user) {
        return new UserCredentialDTO(
                user.getName(),
                user.getEmail()
        );
    }

    private UserCredential mapToUser(UserCredentialDTO credential) {
        if (credential == null) {
            throw new CustomValidationException("Not valid user",
                    "USER_NOT_VALID");
        }
        return new UserCredential(
                credential.getUsername(),
                credential.getEmail(),
                passwordEncoder.encode(credential.getPassword())
        );
    }

    private boolean userExist(UserCredentialDTO credential) {
        return this.userCredentialRepository.findByName(credential.getUsername()).isPresent() ||
                this.userCredentialRepository.findByEmail(credential.getEmail()).isPresent();
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public List<UserCredential> getAll() {
        return this.userCredentialRepository.findAll();
    }
}

package com.homecode.service;

import com.homecode.model.UserCredential;
import com.homecode.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        this.userCredentialRepository.save(credential);
        return "user saved to repository";
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

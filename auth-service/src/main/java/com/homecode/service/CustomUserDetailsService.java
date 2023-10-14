package com.homecode.service;

import com.homecode.config.CustomUserDetails;
import com.homecode.model.UserCredential;
import com.homecode.repository.UserCredentialRepository;
import com.homecode.commons.exception.CustomNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@AllArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private UserCredentialRepository userCredentialRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> credential = this.userCredentialRepository.findByName(username);
        return credential.map(CustomUserDetails::new).orElseThrow(() -> new CustomNotFoundException("User not found whit name " + username, "USER_NOT_FOUND"));
    }
}

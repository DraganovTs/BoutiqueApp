package com.homecode.auth.controller;

import com.homecode.auth.model.UserCredential;
import com.homecode.exception.CustomIllegalStateException;
import com.homecode.auth.model.model.UserCredentialDTO;
import com.homecode.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.utils.Web.API;

@RequiredArgsConstructor
@RestController
@RequestMapping(API + "/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserCredentialDTO> addNewUser(@RequestBody UserCredentialDTO user) {
        return authService.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody UserCredentialDTO user) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authenticate.isAuthenticated()){
        return authService.generateToken(user.getUsername());
        }else {
            throw new CustomIllegalStateException("Invalid access","INVALID_ACCESS");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }

    @GetMapping("/all")
    public List<UserCredential> getAll(){
        return this.authService.getAll();
    }
}

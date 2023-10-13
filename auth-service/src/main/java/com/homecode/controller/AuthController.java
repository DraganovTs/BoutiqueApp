package com.homecode.controller;

import com.homecode.model.UserCredential;
import com.homecode.model.model.UserCredentialDTO;
import com.homecode.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.commons.utils.Web.API;

@RequiredArgsConstructor
@RestController
@RequestMapping(API + "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserCredentialDTO> addNewUser(@RequestBody UserCredentialDTO user) {
        return authService.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody UserCredentialDTO user) {
        return authService.generateToken(user.getUsername());
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

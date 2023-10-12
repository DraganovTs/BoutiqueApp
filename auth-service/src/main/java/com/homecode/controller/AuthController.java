package com.homecode.controller;

import com.homecode.model.UserCredential;
import com.homecode.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.commons.utils.Web.API;

@RequiredArgsConstructor
@RestController
@RequestMapping(API + "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String addNewUser(@RequestBody  UserCredential user) {
        return authService.saveUser(user);
    }

    @GetMapping("/token")
    public String getToken(UserCredential user) {
        return authService.generateToken(user.getName());
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

package com.homecode.auth.model.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialDTO {
    private String username;
    private String password;
    private String repeatPassword;
    private String email;

    public UserCredentialDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

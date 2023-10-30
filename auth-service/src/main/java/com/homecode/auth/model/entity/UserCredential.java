package com.homecode.auth.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user_credential")
public class UserCredential extends AbstractEntity {
    private String name;
    private String email;
    private String password;
}

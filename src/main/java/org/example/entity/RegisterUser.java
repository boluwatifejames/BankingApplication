package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterUser {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}

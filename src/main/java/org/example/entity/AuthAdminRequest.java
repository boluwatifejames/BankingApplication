package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class AuthAdminRequest {
    private String email;
    private String password;
}

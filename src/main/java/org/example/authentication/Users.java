package org.example.authentication;

import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private  String lastName;
    @Column(unique = true)
    private  String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role ;
}

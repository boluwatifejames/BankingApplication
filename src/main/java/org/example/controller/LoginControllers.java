package org.example.controller;

import org.example.entity.*;
import org.example.service.ApplicationImplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginControllers {
    @Autowired
    private ApplicationImplService applicationImplService;
    @PostMapping("/neptune/user/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUser registerRequest){
        return ResponseEntity.ok(applicationImplService.registerUser(registerRequest));
    }


    @PostMapping("/neptune/user/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateAndGetToken(@RequestBody AuthenticateUserRequest authenticateUserRequest){
        return ResponseEntity.ok(applicationImplService.authenticateAndGetToken(authenticateUserRequest));
    }


    @PostMapping("/neptune/admin/authenticate")
    public ResponseEntity<AuthAdminResponse> authenticateAdmin(@RequestBody AuthAdminRequest authAdminRequest){
        return ResponseEntity.ok(applicationImplService.authenticateAdmin(authAdminRequest));
    }

}

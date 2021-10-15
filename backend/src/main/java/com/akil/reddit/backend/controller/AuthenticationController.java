package com.akil.reddit.backend.controller;

import com.akil.reddit.backend.dto.AuthenticationResponse;
import com.akil.reddit.backend.dto.LoginRequest;
import com.akil.reddit.backend.dto.RegisterRequests;
import com.akil.reddit.backend.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;


    //Sign up - accept Request object, save data and sent email verification token
    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterRequests registerRequests){
        authService.register(registerRequests);
        return new ResponseEntity<>("User Registration Successful",
                HttpStatus.OK);
    }

    // Used for verifying email token sent during signup and activating account
    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

}

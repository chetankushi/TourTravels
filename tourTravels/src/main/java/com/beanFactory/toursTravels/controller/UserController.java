package com.beanFactory.toursTravels.controller;

import com.beanFactory.toursTravels.dto.UserLoginDto;
import com.beanFactory.toursTravels.dto.UserRegisterDto;
import com.beanFactory.toursTravels.service.JwtService;
import com.beanFactory.toursTravels.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService  jwtService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto user){
        if(userService.existsByUsername(user.getUserName())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }
        UserRegisterDto registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsernameExists(@RequestParam String username){
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UserLoginDto user){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getUsername());
            }
        } catch (AuthenticationException ex) {
            return "Authentication failed: " + ex.getMessage();
        }
        return "Failing for unknown reason";
    }

}

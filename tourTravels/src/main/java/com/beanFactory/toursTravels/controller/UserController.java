package com.beanFactory.toursTravels.controller;

import com.beanFactory.toursTravels.dto.UserLoginDto;
import com.beanFactory.toursTravels.dto.UserRegisterDto;
import com.beanFactory.toursTravels.service.JwtService;
import com.beanFactory.toursTravels.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST})
public class UserController {

    private Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService  jwtService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto user){
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
    public ResponseEntity<?> login(@RequestBody UserLoginDto user){
        LOGGER.info(user.toString());
        LOGGER.info("LOGIN");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(user.getUsername());
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                return  ResponseEntity.ok(response);
            } else {
              return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
            }
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failing for unknown reason");
        }
    }

}

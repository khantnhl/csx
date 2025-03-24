package com.csx.app.controller;

import com.csx.app.config.JWTUtils;
import com.csx.app.model.User;
import com.csx.app.repository.UserRepository;
import com.csx.app.service.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserDao userdao;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPasswordHash()
                    )
            );
            // if authentication success -> load the users
            final UserDetails userDetails = userdao.findUserByEmail(user.getEmail());
            //generate jwt token
            final Map<String, Object> claims = new HashMap<>();

            final String token = jwtUtils.generateToken(userDetails, claims);

            return ResponseEntity.ok("Login Success!" + token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Failed" + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body("User Exists!");
        }

        User newuser = new User();
        newuser.setEmail(user.getEmail());
        newuser.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        newuser.setUsername(user.getUsername());

        // save to database
        User savedUser = userRepository.save(newuser);

        //generate JWT Token
        final UserDetails userDetails = userdao.findUserByEmail(savedUser.getEmail());
        final Map<String, Object> claims = new HashMap<>();

        final String token = jwtUtils.generateToken(userDetails, claims);

        //returns token
        return ResponseEntity.ok(token);
    }


}
package com.csx.app.controller;

import java.util.List;
import java.util.Optional;

import com.csx.app.model.User;
import com.csx.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

//REST API -> user services

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService _userservice;

    public UserController(UserService userservice)
    {
        this._userservice = userservice;
    }

    //get all users (admin only)
    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<User>> getAllUsers(){

        List<User> users = _userservice.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //get logged in user profile
    @GetMapping("/profile")
    public ResponseEntity<Optional<User>> getUserProfile(@AuthenticationPrincipal UserDetails userDetails){

        Optional<User> user = _userservice.findByEmail(userDetails.getUsername());

        return ResponseEntity.ok(user);
    }


}

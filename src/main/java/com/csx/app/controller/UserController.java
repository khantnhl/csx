package com.csx.app.controller;

import java.util.List;

import com.csx.app.model.User;
import com.csx.app.service.UserService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public List<User> getAllUsers(){
        return _userservice.getAllUsers();
    }

    //post req
    @PostMapping
    public ResponseEntity<User> createUser(User user){
        User newUser = _userservice.createUser(user);
        System.out.print("Created New User");
        return ResponseEntity.ok(newUser);
    }

}

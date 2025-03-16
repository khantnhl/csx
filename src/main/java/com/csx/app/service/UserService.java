package com.csx.app.service;

import com.csx.app.model.User;
import com.csx.app.repository.UserRepository;

import java.util.List;

import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    private final UserRepository _userRepository;
//    private final BCryptPasswordEncoder _encoder;


    public UserService(UserRepository userRepository)
    {
        this._userRepository = userRepository;
//        this._encoder = new BCryptPasswordEncoder();
    }

    public List<User> getAllUsers() {
        return _userRepository.findAll();
    }

    public User createUser(User user){
        return _userRepository.save(user);
    }

}

package com.csx.app.service;

import com.csx.app.model.User;
import com.csx.app.repository.UserRepository;

import java.util.List;

import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    private final UserRepository _userRepository;

    public UserService(UserRepository userRepository)
    {
        this._userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return _userRepository.findAll();
    }

    public User createUser(User user){
        return _userRepository.save(user);
    }

}

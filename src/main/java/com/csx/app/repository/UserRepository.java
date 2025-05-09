package com.csx.app.repository;

import java.util.List;
import java.util.Optional;

import com.csx.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//uses interface, defines methods that a class must implement but no implementation in interface, gives templates to classes
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}

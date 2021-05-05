package com.dms.rest;

import com.dms.dao.UserRepository;
import com.dms.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/get")
    public Optional<User> findById(@RequestParam Long id) {
        return userRepository.findById(id);
    }
}

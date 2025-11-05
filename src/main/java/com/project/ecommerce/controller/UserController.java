package com.project.ecommerce.controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.domain.User;
import com.project.ecommerce.service.UserService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.handleCreate(user));
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws Exception {
        if (this.userService.getUserById(user.getId())==null) {
            throw new Exception("Id invalid");
        }
        return ResponseEntity.ok().body(this.userService.handleUpdate(user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable ("id") Long id){
        this.userService.handleDelete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable ("id") Long id) {
        return ResponseEntity.ok().body(this.userService.getUserById(id));
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> list =this.userService.handleGetAll();
        return ResponseEntity.ok().body(list);
    }


}

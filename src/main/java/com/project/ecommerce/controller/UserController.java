package com.project.ecommerce.controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.domain.User;
import com.project.ecommerce.domain.response.ResCreateUser;
import com.project.ecommerce.domain.response.ResFetchUser;
import com.project.ecommerce.domain.response.ResUpdateUser;
import com.project.ecommerce.service.UserService;
import com.project.ecommerce.ultil.error.IdInvalidException;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<ResCreateUser> createUser(@Valid @RequestBody User user) throws IdInvalidException {
        boolean check=this.userService.exitsByEmail(user.getEmail());
        if (check==true) {
            throw new IdInvalidException("email is exists");
        }
        User cur=this.userService.handleCreate(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUser(cur));
    }

    @PutMapping("/users")
    public ResponseEntity<ResUpdateUser> updateUser(@RequestBody User user) throws IdInvalidException {
        if (this.userService.getUserById(user.getId())==null) {
            throw new IdInvalidException("user is valid");
        }
        User cur=this.userService.handleCreate(user);
        return ResponseEntity.ok().body(this.userService.convertToResUpdateUser(cur));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable ("id") Long id) throws IdInvalidException{
        User user=this.userService.getUserById(id);
        if (user == null) {
            throw new IdInvalidException("Id invalid");
        }
        this.userService.handleDelete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResFetchUser> getUserById(@PathVariable ("id") Long id) {
        User cur=this.userService.getUserById(id);
        return ResponseEntity.ok().body(this.userService.convertToResFetchUser(cur));
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> list =this.userService.handleGetAll();
        return ResponseEntity.ok().body(list);
    }


}

package com.project.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.ecommerce.domain.User;
import com.project.ecommerce.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreate(User user){
        return this.userRepository.save(user);
    }

    public User getUserById(Long id){
        Optional<User> user=this.userRepository.findById(id);
        return user.isPresent() ? user.get() : null;
    }

    public User handleUpdate(User user){
        User curUser =getUserById(user.getId());
        if (curUser !=null) {
            curUser.setId(user.getId());
            curUser.setName(user.getName());
            curUser.setAddress(user.getAddress());
            curUser.setEmail(user.getEmail());
            curUser.setGender(user.getGender());
            curUser.setPassword(user.getPassword());
            curUser.setCreatedAt(user.getCreatedAt());
            curUser.setUpdatedAt(user.getUpdatedAt());
            curUser.setCreatedBy(user.getCreatedBy());
            curUser.setUpdateBy(user.getUpdateBy());

            user=this.userRepository.save(curUser);
        }
        return user;
    }

    public List<User> handleGetAll(){
        return this.userRepository.findAll();
    }

    public void handleDelete(Long id){
        this.userRepository.deleteById(id);
    }
}

package com.readmee.readme.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.readmee.readme.models.User;
import com.readmee.readme.services.UserService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@PreAuthorize("hasAnyAuthority('admin', 'user')")
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService service;
     
    @PostMapping
     public User createUser(@Valid @RequestBody User user) {
        return service.createUser(user);
     }

     @GetMapping
     public List<User> findAllUsers() {
        return service.findAllUsers();
     }

     @GetMapping("/find-me/{user_id}")
     public Optional<User> getUserByUserId(@PathVariable UUID user_id) {
         return service.getUserByUserId(user_id.toString() );
     }
     
     
     @GetMapping("/{id}")
     public User getUserById(@PathVariable Integer id) {
         return service.getUserById(id);
     }

     @DeleteMapping("/{id}")
     public void deleteUser(@PathVariable Integer id){
        service.deleteUser(id);
     }

     @PutMapping("/{user_id}/books/{book_id}")
     public void addFavorite(@PathVariable Integer user_id, @PathVariable Integer book_id) {
        service.addFavorite(user_id, book_id);
     }
     
}

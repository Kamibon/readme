package com.readmee.readme.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.readmee.readme.models.User;
import com.readmee.readme.services.UserService;

import jakarta.validation.Valid;

@RestController
@PreAuthorize("hasAnyAuthority('admin', 'user')")
@RequestMapping("/api/users")
public class UserController {
   @Autowired
   UserService service;

   @PostMapping
   public User createUser(@Valid @RequestBody User user) {
      return service.createUser(user);
   }

   @GetMapping
   public Page<User> findAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
      return service.findAllUsers(page, size);
   }

   @GetMapping("/find-me/{user_id}")
   public Optional<User> getUserByUserId(@PathVariable UUID user_id) {
      return service.getUserByUserId(user_id.toString());
   }

   @GetMapping("/{id}")
   public User getUserById(@PathVariable Integer id) {
      return service.getUserById(id);
   }

   @DeleteMapping("/{id}")
   public void deleteUser(@PathVariable Integer id) {
      service.deleteUser(id);
   }

   @PutMapping("/{user_id}/books/{book_id}")
   public void addFavorite(@PathVariable Integer user_id, @PathVariable Integer book_id) {
      service.addFavorite(user_id, book_id);
   }

   @PostMapping("/{followerId}/follow/{followedId}")
   @ResponseStatus(code = HttpStatus.ACCEPTED)
   public void followUser(@PathVariable Integer followerId, @PathVariable Integer followedId) {
      service.followUser(followerId, followedId);
   }
}

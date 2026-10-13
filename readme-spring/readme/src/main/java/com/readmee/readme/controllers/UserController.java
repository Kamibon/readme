package com.readmee.readme.controllers;

import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readmee.readme.dtos.CreateUserRequest;
import com.readmee.readme.dtos.UserResponse;
import com.readmee.readme.models.User;
import com.readmee.readme.services.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor 
@PreAuthorize("hasAnyAuthority('admin', 'user')")
@RequestMapping("/api/users")
public class UserController {

    UserService service;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest user) {
         return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createUser(user));
    }

    @GetMapping
    public Page<UserResponse> findAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return service.findAllUsers(page, size);
    }

    @GetMapping("/find-me/{user_id}")
    public ResponseEntity<UserResponse> getUserByUserId(@PathVariable UUID user_id) {
        return ResponseEntity.ok(service.getUserByUserId(user_id.toString()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) throws NotFoundException{
        return ResponseEntity.ok(service.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

    @PutMapping("/{user_id}/books/{book_id}")
    public void addFavorite(@PathVariable Long user_id, @PathVariable Long book_id) throws NotFoundException{
        service.addFavorite(user_id, book_id);
    }

    @PreAuthorize("#id == authentication.token.subject")
    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id, @RequestBody User user) {
        service.updateUser(id, user);
    }
}

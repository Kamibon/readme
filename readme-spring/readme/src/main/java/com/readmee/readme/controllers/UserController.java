package com.readmee.readme.controllers;

import java.util.UUID;

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
import com.readmee.readme.dtos.UpdateUserRequest;
import com.readmee.readme.dtos.UserResponse;
import com.readmee.readme.services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@PreAuthorize("hasAnyAuthority('admin', 'user')")
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createUser(user));
    }

    @GetMapping
    public Page<UserResponse> findAllUsers(@RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        return service.findAllUsers(page, size);
    }

    @PreAuthorize("#userId.toString() == authentication.token.subject or hasAuthority('admin')")
    @GetMapping("/find-me/{userId}")
    public ResponseEntity<UserResponse> getUserByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.getUserByUserId(userId.toString()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable @Positive  @NotNull Long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id.toString() == authentication.token.subject or hasAuthority('admin')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/books/{bookId}")
    public ResponseEntity<Void> addFavorite(@PathVariable @NotNull String userId, @PathVariable @Positive  @NotNull Long bookId) {
        service.addFavorite(userId, bookId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("#id == authentication.token.subject")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable @NotNull String id, @Valid @RequestBody UpdateUserRequest user) {
        return ResponseEntity.ok(service.updateUser(id, user));
    }
}

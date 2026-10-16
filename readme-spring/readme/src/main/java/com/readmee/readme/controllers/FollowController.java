package com.readmee.readme.controllers;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readmee.readme.dtos.UserResponse;
import com.readmee.readme.models.Follow;
import com.readmee.readme.services.FollowService;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor 
@RequestMapping("/api/follows")
public class FollowController {

    private FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<Follow> followUser(@RequestParam @Positive @NotNull  Long followerId, @RequestParam @Positive @NotNull Long followedId) {
        return ResponseEntity.ok(followService.followUser(followerId, followedId));
    }

    @PostMapping("/unfollow")
    public ResponseEntity<Void> unfollowUser(@RequestParam @Positive @NotNull Long followerId, @RequestParam @Positive @NotNull Long followedId) throws NotFoundException {
        followService.unfollowUser(followerId, followedId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/follows")
    public ResponseEntity<List<UserResponse>> getFollows(@RequestParam @Positive @NotNull Long followerId) {
        List<UserResponse> followedUsers = followService.getFollows(followerId);
        return ResponseEntity.ok(followedUsers);
    }

    @GetMapping("/followers")
    public ResponseEntity<List<UserResponse>> getFollowers(@RequestParam @Positive @NotNull Long followedId) {
        List<UserResponse> followers = followService.getFollowers(followedId);
        return ResponseEntity.ok(followers);
    }
}


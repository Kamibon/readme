package com.readmee.readme.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readmee.readme.models.User;
import com.readmee.readme.repositories.UserRepository;
import com.readmee.readme.services.FollowService;

@RestController
@RequestMapping("/api/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestParam Integer followerId, @RequestParam Integer followedId) {
        try {
            followService.followUser(followerId, followedId);
            return ResponseEntity.status(HttpStatus.OK).body("User followed successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestParam Integer followerId, @RequestParam Integer followedId) {
        try {
            followService.unfollowUser(followerId, followedId);
            return ResponseEntity.status(HttpStatus.OK).body("User unfollowed successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/follows")
    public ResponseEntity<List<User>> getFollows(@RequestParam Integer followerId) {
        List<User> followedUsers = followService.getFollows(followerId);
        return ResponseEntity.ok(followedUsers);
    }

    @GetMapping("/followers")
    public ResponseEntity<List<User>> getFollowers(@RequestParam Integer followedId) {
        List<User> followers = followService.getFollowers(followedId);
        return ResponseEntity.ok(followers);
    }
}


package com.readmee.readme.controllers;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readmee.readme.models.Follow;
import com.readmee.readme.models.User;
import com.readmee.readme.services.FollowService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor 
@RequestMapping("/api/follows")
public class FollowController {

    private FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<Follow> followUser(@RequestParam Long followerId, @RequestParam Long followedId) {
        return ResponseEntity.ok(followService.followUser(followerId, followedId));
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestParam Long followerId, @RequestParam Long followedId) throws NotFoundException {
        return  ResponseEntity.ok( followService.unfollowUser(followerId, followedId));
    }

    @GetMapping("/follows")
    public ResponseEntity<List<User>> getFollows(@RequestParam Long followerId) {
        List<User> followedUsers = followService.getFollows(followerId);
        return ResponseEntity.ok(followedUsers);
    }

    @GetMapping("/followers")
    public ResponseEntity<List<User>> getFollowers(@RequestParam Long followedId) {
        List<User> followers = followService.getFollowers(followedId);
        return ResponseEntity.ok(followers);
    }
}


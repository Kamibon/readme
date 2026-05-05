package com.readmee.readme.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmee.readme.models.Follow;
import com.readmee.readme.models.User;
import com.readmee.readme.repositories.FollowRepository;
import com.readmee.readme.repositories.UserRepository;

@Service
public class FollowService {

    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void followUser(Integer followerId, Integer followedId) {
        if (followerId.equals(followedId)) {
            throw new RuntimeException("An user cannot follow themselves");
        }

        if (followRepository.findByFollowerIdAndFollowedId(followerId, followedId).isPresent()) {
            throw new RuntimeException("You already follow this user");
        }

        Follow follow = new Follow(followerId, followedId);
        followRepository.save(follow);
    }

    @Transactional
    public void unfollowUser(Integer followerId, Integer followedId) {
        Follow follow = followRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                .orElseThrow(() -> new RuntimeException("Follow relationship not found"));

        followRepository.delete(follow);
    }

    public List<User> getFollows(Integer followerId) {
        List<Follow> follows = followRepository.findByFollowerId(followerId);
        List<Integer> followedIds = follows.stream()
                .map(Follow::getFollowedId)
                .collect(Collectors.toList());
        return userRepository.findAllById(followedIds);
    }

    public List<User> getFollowers(Integer followedId) {
        List<Follow> followers = followRepository.findByFollowedId(followedId);
        List<Integer> followerIds = followers.stream()
                .map(Follow::getFollowerId)
                .collect(Collectors.toList());
        return userRepository.findAllById(followerIds);
    }
}

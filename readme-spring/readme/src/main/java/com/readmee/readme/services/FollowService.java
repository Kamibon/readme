package com.readmee.readme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmee.readme.models.Follow;
import com.readmee.readme.models.User;
import com.readmee.readme.repositories.FollowRepository;
import com.readmee.readme.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FollowService {

    FollowRepository followRepository;

    UserRepository userRepository;

    @Transactional
    public Follow followUser(Long followerId, Long followedId) {
        if (followerId.equals(followedId)) {
            throw new RuntimeException("An user cannot follow themselves");
        }

        if (followRepository.findByFollowerIdAndFollowedId(followerId, followedId).isPresent()) {
            throw new RuntimeException("You already follow this user");
        }

        Follow follow = new Follow(followerId, followedId);
        return followRepository.save(follow);
    }

    @Transactional
    public String unfollowUser(Long followerId, Long followedId) throws NotFoundException {
        Follow follow = followRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                .orElseThrow(() -> new NotFoundException());

        followRepository.delete(follow);

        return "User unfollowed";
    }

    public List<User> getFollows(Long followerId) {
        List<Follow> follows = followRepository.findByFollowerId(followerId);
        Set<Long> followedIds = follows.stream()
                .map(follower -> follower.getFollowedId())
                .collect(Collectors.toSet());
        return userRepository.findAllById(followedIds);
    }

    public List<User> getFollowers(Long followedId) {
        List<Follow> followers = followRepository.findByFollowedId(followedId);
    
        Set<Long> followerIds = followers.stream()
                .map(follower -> follower.getFollowerId())
                .collect(Collectors.toSet());
        return userRepository.findAllById(followerIds);
    }
}

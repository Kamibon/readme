package com.readmee.readme.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmee.readme.dtos.UserResponse;
import com.readmee.readme.mappers.UserMapper;
import com.readmee.readme.models.Follow;
import com.readmee.readme.repositories.FollowRepository;
import com.readmee.readme.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class FollowService {

    private final FollowRepository followRepository;

    private final UserRepository userRepository;

    private final UserMapper mapper;

    @Transactional
    public Follow followUser(Long followerId, Long followedId) {
        if (followerId.equals(followedId)) {
            throw new IllegalArgumentException("An user cannot follow themselves");
        }

        if (!userRepository.existsById(followerId)
                || !userRepository.existsById(followedId)) {
            throw new EntityNotFoundException(
                    "Follower or followed user does not exist");
        }

        if (followRepository.findByFollowerIdAndFollowedId(followerId, followedId).isPresent()) {
            throw new IllegalArgumentException("You already follow this user");
        }

        Follow follow = new Follow(followerId, followedId);
        return followRepository.save(follow);
    }

    @Transactional
    public void unfollowUser(Long followerId, Long followedId) {
        Follow follow = followRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                .orElseThrow(() -> new EntityNotFoundException("You don't follow this user"));

        followRepository.delete(follow);

    }

    public List<UserResponse> getFollows(Long followerId) {
    return followRepository.findByFollowerId(followerId)
            .stream()
            .map(follow -> follow.getFollowedId())
            .collect(Collectors.collectingAndThen(
                    Collectors.toList(),
                    userRepository::findAllById))
            .stream()
            .map(mapper::toResponse)
            .toList();
}

    public List<UserResponse> getFollowers(Long followedId) {
        List<Follow> followers = followRepository.findByFollowedId(followedId);

        Set<Long> followerIds = followers.stream()
                .map(follower -> follower.getFollowerId())
                .collect(Collectors.toSet());
        return userRepository.findAllById(followerIds).stream().map(user -> mapper.toResponse(user)).toList();
    }
}

package com.readmee.readme.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readmee.readme.models.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerIdAndFollowedId(Long followerId, Long followedId);

    List<Follow> findByFollowerId(Long followerId);

    List<Follow> findByFollowedId(Long followedId);

}

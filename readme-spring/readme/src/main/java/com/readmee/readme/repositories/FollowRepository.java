package com.readmee.readme.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readmee.readme.models.Follow;
import com.readmee.readme.models.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerIdAndFollowedId(Integer followerId, Integer followedId);

    List<Follow> findByFollowerId(Integer followerId);

    List<Follow> findByFollowedId(Integer followedId);

}

package com.readmee.readme.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "follows", uniqueConstraints = {
        @UniqueConstraint(name = "uk_follow_follower_followed", columnNames = { "follower_id", "followed_id" })
})
@NoArgsConstructor
@Getter
@Setter
public class Follow extends BaseEntity {

    @Column(name = "follower_id", nullable = false)
    private Long followerId;

    @Column(name = "followed_id", nullable = false)
    private Long followedId;

    public Follow(Long followerId, Long followedId) {
        this.followerId = followerId;
        this.followedId = followedId;
    }
}

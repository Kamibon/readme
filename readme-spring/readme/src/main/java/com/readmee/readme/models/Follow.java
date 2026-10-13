package com.readmee.readme.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "follows")
@NoArgsConstructor
@Getter
@Setter
public class Follow extends BaseEntity{

    @Column(name = "follower_id")
    private Long followerId;

    @Column(name = "followed_id")
    private Long followedId;

    public Follow(Long followerId, Long followedId) {
        this.followerId = followerId;
        this.followedId = followedId;
    }
}

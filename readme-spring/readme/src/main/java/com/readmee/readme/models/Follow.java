package com.readmee.readme.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follows")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follower_id")
    private Integer followerId;

    @Column(name = "followed_id")
    private Integer followedId;

    public Follow(Integer followerId, Integer followedId) {
        this.followerId = followerId;
        this.followedId = followedId;
    }
}

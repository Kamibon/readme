package com.readmee.readme.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String userId;

    @ManyToMany
    @Builder.Default
    @JoinTable(
        name = "user_favorites",  
        joinColumns = @JoinColumn(name = "user_id"),  
        inverseJoinColumns = @JoinColumn(name = "book_id") 
    )
    private Set<Book> favorites = new HashSet<>();
}


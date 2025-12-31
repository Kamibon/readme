package com.readmee.readme.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "L'username non può essere vuoto")
    @Column(unique = true)
    private String username;

    @Email(message = "Email non valida")
    private String email;

    private String userId;

    @ManyToMany
    @JoinTable(
        name = "user_favorites",  
        joinColumns = @JoinColumn(name = "user_id"),  
        inverseJoinColumns = @JoinColumn(name = "book_id") 
    )
    private Set<Book> favorites = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "user_following", 
        joinColumns = @JoinColumn(name = "user_id"),  
        inverseJoinColumns = @JoinColumn(name = "following_id") 
    )
    private Set<User> follows = new HashSet<>();

    @ManyToMany(mappedBy = "follows")
    private Set<User> followed = new HashSet<>();
}

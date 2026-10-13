package com.readmee.readme.repositories;

import com.readmee.readme.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    long countByFavoritesContaining(Book book);
}

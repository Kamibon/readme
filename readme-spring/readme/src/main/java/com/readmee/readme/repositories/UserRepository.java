package com.readmee.readme.repositories;

import org.springframework.stereotype.Repository;
import com.readmee.readme.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;



@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(String userId);
}

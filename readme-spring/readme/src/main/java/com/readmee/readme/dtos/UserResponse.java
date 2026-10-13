package com.readmee.readme.dtos;

import java.util.Date;
import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        String email,
        String userId,
        Set<Long> favorites,
        Date createdAt,
        Date updatedAt) {

}

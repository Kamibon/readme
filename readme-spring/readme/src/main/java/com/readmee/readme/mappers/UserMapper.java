package com.readmee.readme.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.readmee.readme.dtos.UserResponse;
import com.readmee.readme.models.Book;
import com.readmee.readme.models.User;

@Component 
public class UserMapper {
    public UserResponse toResponse(User user) {
        Set<Long> ids = user.getFavorites().stream()
        .map(Book::getId)
        .collect(Collectors.toSet());
        UserResponse response = new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getUserId(),
                ids, user.getCreatedAt(), user.getUpdatedAt());
        return response;
    }
}

package com.readmee.readme.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter 
public class UpdateUserRequest {
    @NotEmpty(message = "L'username non può essere vuoto")
    private String username;
}

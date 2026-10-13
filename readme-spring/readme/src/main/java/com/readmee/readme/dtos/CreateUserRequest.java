package com.readmee.readme.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter 
public class CreateUserRequest {
    @NotEmpty(message = "L'username non può essere vuoto")
    private String username;

    @Email(message = "Email non valida")
    private String email;

}

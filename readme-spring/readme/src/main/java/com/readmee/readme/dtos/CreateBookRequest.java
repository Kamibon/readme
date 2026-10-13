package com.readmee.readme.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter 
public class CreateBookRequest {
    @NotNull
    private String title;
    @NotNull
    private String isbn;
    @NotNull 
    private String author;
    private Integer year;
    private String genre;
    private Boolean available;
    private String description;
}

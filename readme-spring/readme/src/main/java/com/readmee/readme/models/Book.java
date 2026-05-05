package com.readmee.readme.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "books")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book extends  BaseEntity {
    
    @NotNull
    private String title;
    @NotNull
    private String isbn;
    private String author;
    private Integer year;
    private String genre;
    private Boolean available;
    private String description;
}

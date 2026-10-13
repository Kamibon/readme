package com.readmee.readme.dtos;

import java.util.Date;

public record BookResponse(
        Long id,
        String title,
        String isbn,
        String author,
        Integer year,
        String genre,
        Boolean available,
        String description,
        Date createdAt,
        Date updatedAt) {

}

package com.readmee.readme.mappers;

import org.springframework.stereotype.Component;

import com.readmee.readme.dtos.BookResponse;
import com.readmee.readme.models.Book;

@Component
public final class BookMapper {
    public BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getAuthor(),
                book.getYear(),
                book.getGenre(),
                book.getAvailable(),
                book.getDescription(),
                book.getCreatedAt(),
                book.getUpdatedAt());

        return response;
    }
}

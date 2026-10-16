package com.readmee.readme.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readmee.readme.dtos.BookResponse;
import com.readmee.readme.dtos.CreateBookRequest;
import com.readmee.readme.services.BookService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@RestController
@PreAuthorize("hasAnyAuthority('admin', 'user')")
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    @GetMapping
    public Page<BookResponse> getBooks(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {

        return service.findAllBooks(page, size);
    }

    @GetMapping("/by-ids")
    public List<BookResponse> getBooksByIds(
            @NotEmpty @Size(max = 100) @RequestParam List<@NotNull @Positive Long> ids) {

        return service.getBooksByIds(ids);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable @Positive  @NotNull  Long id) {
        return ResponseEntity.ok(service.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid CreateBookRequest book) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createBook(book));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> deleteBook(@PathVariable @Positive  @NotNull Long id) {
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}

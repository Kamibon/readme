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

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;

@RestController
@PreAuthorize("hasAnyAuthority('admin', 'user')")
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    BookService service;

    @GetMapping
    public Page<BookResponse> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.findAllBooks(page, size);
    }

    @GetMapping("/by-ids")
    public List<BookResponse> getBooksByIds(
            @RequestParam List<Long> ids) {

        return service.getBooksByIds(ids);
    }

    @GetMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody CreateBookRequest book) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createBook(book));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
    }

}

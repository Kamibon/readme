package com.readmee.readme.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readmee.readme.models.Book;
import com.readmee.readme.services.BookService;

import jakarta.annotation.security.RolesAllowed;


@RestController
@PreAuthorize("hasAnyAuthority('admin', 'user')")
@RequestMapping("/api/books")
public class BookController {
    
    @Autowired
    BookService service;

    @GetMapping
    public Page<Book> getBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        return service.findAllBooks(page, size);
    }

    @GetMapping("/{id}")
    @RolesAllowed("user")
    public Book getBookById(@PathVariable Integer id) {
        return service.getBookById(id);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
       return service.createBook(book);
    }
    
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id){
        service.deleteBook(id);
    }
    
}

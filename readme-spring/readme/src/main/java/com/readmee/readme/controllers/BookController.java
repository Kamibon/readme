package com.readmee.readme.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readmee.readme.models.Book;
import com.readmee.readme.services.BookService;

import jakarta.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@PreAuthorize("hasAnyAuthority('admin', 'user')")
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    BookService service;

    @GetMapping
    public List<Book> getBooks() {

        return service.findAllBooks();
    }

    @GetMapping("/{id}")
    @RolesAllowed("user")
    public Optional<Book> getBookById(@PathVariable Integer id) {
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

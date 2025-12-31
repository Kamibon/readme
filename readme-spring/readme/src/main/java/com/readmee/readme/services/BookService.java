package com.readmee.readme.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readmee.readme.models.Book;
import com.readmee.readme.repositories.BookRepository;
@Service
public class BookService {
    @Autowired
    BookRepository repository;
public Book createBook(Book book) {
         Book request = Book.builder().title(book.getTitle()).isbn(book.getIsbn()).build();
         
         repository.save(request);
         
         return request;
     }

     public List<Book> findAllBooks() {
        return repository.findAll();
     }
     
     public Optional<Book> getBookById(Integer id) {
         return repository.findById(id);
     }

     public void deleteBook(Integer id){
        repository.deleteById(id);
     }
}

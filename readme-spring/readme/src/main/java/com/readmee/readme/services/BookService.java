package com.readmee.readme.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readmee.readme.models.Book;
import com.readmee.readme.repositories.BookRepository;
import com.readmee.readme.repositories.UserRepository;
@Service
public class BookService {
    @Autowired
    BookRepository repository;

    @Autowired
    UserRepository userRepository;
public Book createBook(Book book) {
         Book request = Book.builder().title(book.getTitle()).isbn(book.getIsbn()).build();
         
         repository.save(request);
         
         return request;
     }

     public List<Book> findAllBooks() {
        return repository.findAll();
     }
     
     public Book getBookById(Integer id) {
        Book book = repository.findById(id).orElseThrow();
        long numberOfFavorites = userRepository.countByFavoritesContaining(book);
        book.setDescription(book.getDescription() + " - Numero di persone a cui piace questo libro: " + numberOfFavorites);
         return book;
     }

     public void deleteBook(Integer id){
        repository.deleteById(id);
     }
}

package com.readmee.readme.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.readmee.readme.dtos.BookResponse;
import com.readmee.readme.dtos.CreateBookRequest;
import com.readmee.readme.mappers.BookMapper;
import com.readmee.readme.models.Book;
import com.readmee.readme.repositories.BookRepository;
import com.readmee.readme.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService {

   BookRepository repository;

   UserRepository userRepository;

   BookMapper mapper;

   public BookResponse createBook(CreateBookRequest book) {
      Book request = Book.builder().title(book.getTitle()).isbn(book.getIsbn()).build();

      repository.save(request);

      return mapper.toResponse(request);
   }

   public Page<BookResponse> findAllBooks(int page, int size) {
      Pageable pageable = PageRequest.of(page, size, Sort.by("title"));
      Page<Book> books = repository.findAll(pageable);

      return books.map(mapper::toResponse);
   }

   public List<BookResponse> getBooksByIds(List<Long> ids) {
      return repository.findAllById(ids)
            .stream()
            .map(book -> mapper.toResponse(book))
            .toList();
   }

   public BookResponse getBookById(Long id) {
      Book book = repository.findById(id).orElseThrow();
      long numberOfFavorites = userRepository.countByFavoritesContaining(book);
      book.setDescription(
            book.getDescription() + " - Numero di persone a cui piace questo libro: " + numberOfFavorites);
      return mapper.toResponse(book);
   }

   public void deleteBook(Long id) {
      repository.deleteById(id);
   }
}

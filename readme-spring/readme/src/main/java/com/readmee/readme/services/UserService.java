package com.readmee.readme.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readmee.readme.models.Book;
import com.readmee.readme.models.User;
import com.readmee.readme.repositories.BookRepository;
import com.readmee.readme.repositories.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  @Autowired
  private BookRepository bookRepository;

  public User createUser(User user) {
    User request = User.builder().username(user.getUsername()).email(user.getEmail()).build();

    repository.save(request);

    return request;
  }

  public List<User> findAllUsers() {
    return repository.findAll();
  }

  public User getUserById(Integer id) {
    User user = repository.findById(id).orElseThrow();
    return user;
  }

  public void deleteUser(Integer id) {
    repository.deleteById(id);
  }

  public Optional<User> getUserByUserId(String user_id) {
    return repository.findByUserId(user_id);
  }

  public void addFavorite(Integer userId, Integer bookId) {
      
        User user = repository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));

        if (user.getFavorites().contains(book)) {
          
            user.getFavorites().remove(book);
        } else {
          
            user.getFavorites().add(book);
        }

        repository.save(user);
    }

}

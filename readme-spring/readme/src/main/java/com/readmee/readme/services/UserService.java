package com.readmee.readme.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.readmee.readme.models.Book;
import com.readmee.readme.models.Follow;
import com.readmee.readme.models.User;
import com.readmee.readme.repositories.BookRepository;
import com.readmee.readme.repositories.FollowRepository;
import com.readmee.readme.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private FollowRepository followRepository;

  public User createUser(User user) {
    User request = User.builder().username(user.getUsername()).email(user.getEmail()).build();

    repository.save(request);

    return request;
  }

  public Page<User> findAllUsers(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return repository.findAll(pageable);
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

  @Transactional
  public void followUser(Integer followerId, Integer followedId) {
    if (followerId.equals(followedId)) {
        throw new RuntimeException("An user cannot follow themselves");
    }

    Optional<Follow> existingFollow = followRepository.findByFollowerIdAndFollowedId(followerId, followedId);
    if (existingFollow.isPresent()) {
        followRepository.delete(existingFollow.get());
    }

    Follow follow = new Follow(followerId, followedId);
    followRepository.save(follow);
  }
}

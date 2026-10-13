package com.readmee.readme.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.readmee.readme.dtos.CreateUserRequest;
import com.readmee.readme.dtos.UserResponse;
import com.readmee.readme.mappers.UserMapper;
import com.readmee.readme.models.Book;
import com.readmee.readme.models.User;
import com.readmee.readme.repositories.BookRepository;
import com.readmee.readme.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository repository;

    private BookRepository bookRepository;

    private UserMapper mapper;

    public UserResponse createUser(CreateUserRequest user) {
        User request = User.builder().username(user.getUsername()).email(user.getEmail()).build();

        repository.save(request);

        return mapper.toResponse(request);
    }

    public Page<UserResponse> findAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = repository.findAll(pageable);
        return users.map(mapper::toResponse);
    }

    public UserResponse getUserById(Long id) {
        User user = repository.findById(id).orElseThrow();
        return mapper.toResponse(user);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public void updateUser(String id, User user) {

        User userToEdit = repository.findByUserId(id).orElseThrow();
        userToEdit.setUsername(user.getUsername());
        repository.save(userToEdit);
    }

    public UserResponse getUserByUserId(String user_id) {
        User user = repository.findByUserId(user_id).orElseThrow();
        return mapper.toResponse(user);
    }

    public void addFavorite(Long userId, Long bookId) throws NotFoundException{

        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException());

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException());

        if (user.getFavorites().contains(book)) {

            user.getFavorites().remove(book);
        } else {

            user.getFavorites().add(book);
        }

        repository.save(user);
    }

}

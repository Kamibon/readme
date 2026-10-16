package com.readmee.readme.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmee.readme.dtos.CreateUserRequest;
import com.readmee.readme.dtos.UpdateUserRequest;
import com.readmee.readme.dtos.UserResponse;
import com.readmee.readme.mappers.UserMapper;
import com.readmee.readme.models.Book;
import com.readmee.readme.models.User;
import com.readmee.readme.repositories.BookRepository;
import com.readmee.readme.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository repository;

    private final BookRepository bookRepository;

    private final UserMapper mapper;

    @Transactional 
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
        User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("This user does not exist"));
        return mapper.toResponse(user);
    }

    @Transactional 
    public void deleteUser(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID non valido");
        }
        User user = repository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        repository.deleteById(user.getId());
    }

    @Transactional 
    public UserResponse updateUser(String id, UpdateUserRequest user) {

        User userToEdit = repository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("This user does not exist"));
        userToEdit.setUsername(user.getUsername());

        User editedUser = repository.save(userToEdit);
        return mapper.toResponse(editedUser);
    }

    public UserResponse getUserByUserId(String user_id) {
        User user = repository.findByUserId(user_id).orElseThrow();
        return mapper.toResponse(user);
    }

    public void addFavorite(String userId, Long bookId) {

        User user = repository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException());

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException());

        if (user.getFavorites().contains(book)) {

            user.getFavorites().remove(book);
        } else {

            user.getFavorites().add(book);
        }

        repository.save(user);
    }

}

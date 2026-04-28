package com.readmee.readme;

import com.readmee.readme.models.Book;
import com.readmee.readme.models.User;
import com.readmee.readme.repositories.BookRepository;
import com.readmee.readme.repositories.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public DataInitializer(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Book book1 = Book.builder()
                .title("The Great Gatsby")
                .isbn("9780743273565")
                .author("F. Scott Fitzgerald")
                .year(1925)
                .genre("Fiction")
                .available(true)
                .description("A novel about the American dream.")
                .build();

        Book book2 = Book.builder()
                .title("1984")
                .isbn("9780451524935")
                .author("George Orwell")
                .year(1949)
                .genre("Dystopian")
                .available(true)
                .description("A novel about a totalitarian regime.")
                .build();

        Book book3 = Book.builder()
                .title("To Kill a Mockingbird")
                .isbn("9780061120084")
                .author("Harper Lee")
                .year(1960)
                .genre("Fiction")
                .available(false)
                .description("A novel about racial injustice in the American South.")
                .build();

        bookRepository.saveAll(Arrays.asList(book1, book2, book3));

        User user1 = User.builder()
                .username("john_doe")
                .email("john.doe@example.com")
                .userId("7d35c50d-2d90-4de0-b900-abcd88a86fa5")
                .build();

        User user2 = User.builder()
                .username("jane_smith")
                .email("jane.smith@example.com")
                .userId("0f1850aa-3c39-4934-8ee7-c23ee4e08818")
                .build();

        User user3 = User.builder()
                .username("mike_brown")
                .email("mike.brown@example.com")
                .userId("acaaaf6a-1c5d-49fd-94d5-9e2fbc623a0b")
                .build();

        userRepository.saveAll(Arrays.asList(user1, user2, user3));

        System.out.println("Database initialized.");
    }
}

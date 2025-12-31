package com.readmee.readme;

import com.readmee.readme.models.Book;
import com.readmee.readme.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crea alcuni libri di esempio
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

        // Salva i libri nel database
        bookRepository.saveAll(Arrays.asList(book1, book2, book3));

        System.out.println("Database inizializzato con alcuni libri.");
    }
}

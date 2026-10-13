package com.readmee.readme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readmee.readme.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}

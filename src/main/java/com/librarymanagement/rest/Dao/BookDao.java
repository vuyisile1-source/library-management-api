package com.librarymanagement.rest.Dao;

import com.librarymanagement.rest.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDao extends JpaRepository<Book, Long> {
}

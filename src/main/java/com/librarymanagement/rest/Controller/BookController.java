package com.librarymanagement.rest.Controller;

import com.librarymanagement.rest.Dao.BookDao;
import com.librarymanagement.rest.Dao.UserDao;
import com.librarymanagement.rest.Models.Book;
import com.librarymanagement.rest.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UserDao userDao;

    @PostMapping(value = "/api/books")
    public String addBook(@RequestBody Book book) {
        bookDao.save(book);
        return "Book added!";
    }

    @GetMapping(value = "/api/books")
    public List<Book> getBooks() {
        return bookDao.findAll();
    }

    @GetMapping(value = "/api/books/{id}")
    public Book getBook(@PathVariable long id) {
        return bookDao.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @PutMapping(value = "/api/books/{id}")
    public String updateBook(@PathVariable long id, @RequestBody Book bookDetails) {
        Book book = bookDao.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setBorrowed(bookDetails.getBorrowed());
        bookDao.save(book);
        return "Book updated!";
    }

    @DeleteMapping(value = "/api/books/{id}")
    public String deleteBook(@PathVariable long id) {
        Book deletedBook = bookDao.findById(id).orElseThrow();
        Book book = bookDao.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        bookDao.delete(book);
        return deletedBook.getTitle() + " was deleted successfully";
    }

    @PostMapping(value = "/api/books/{bookId}/borrow/{userId}")
    public String borrowBook(@PathVariable long bookId, @PathVariable long userId) {
        Book book = bookDao.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (book.getBorrowed()) {
            return "Book is not available for borrowing.";
        }

        book.setBorrowed(true);
        book.setBorrowedBy(user);
        bookDao.save(book);
        return "Book borrowed successfully!";
    }

    @PostMapping(value = "/api/books/{bookId}/return")
    public String returnBook(@PathVariable long bookId) {
        Book book = bookDao.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        if (!book.getBorrowed()) {
            return "Book is not currently borrowed.";
        }

        book.setBorrowed(false);
        book.setBorrowedBy(null);
        bookDao.save(book);
        return "Book returned successfully!";
    }
}

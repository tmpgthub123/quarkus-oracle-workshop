package com.enlight.demo.book;

import com.enlight.demo.common.DemoValidationException;
import com.enlight.demo.common.DemoNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Service for the intro Book flow.
 * Keeps endpoint code small and readable.
 */
@ApplicationScoped
public class BookService {

    @Inject
    BookRepository repository;

    public List<Book> listBooks() {
        return repository.findAll();
    }

    public Book getBookById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DemoNotFoundException("Book id " + id + " was not found"));
    }

    public Book createBook(Book request) {
        validate(request);
        return repository.save(request);
    }

    // TODO: Workshop task 1
    // Implement delete flow:
    // - validate id
    // - call repository.deleteById
    // - throw DemoNotFoundException when no row is deleted
    public void deleteBook(Long id) {
        throw new UnsupportedOperationException("TODO: Implement Workshop task 1");
    }

    // TODO: Workshop task 3
    // Implement case-insensitive author search flow.
    public List<Book> searchBooksByAuthor(String author) {
        throw new UnsupportedOperationException("TODO: Implement Workshop task 3");
    }

    private void validate(Book request) {
        if (request == null) {
            throw new DemoValidationException("Book payload is required");
        }
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new DemoValidationException("Book title is required");
        }
        if (request.getAuthor() == null || request.getAuthor().isBlank()) {
            throw new DemoValidationException("Book author is required");
        }
        if (request.getIsbn() == null || request.getIsbn().isBlank()) {
            throw new DemoValidationException("Book ISBN is required");
        }
    }
}

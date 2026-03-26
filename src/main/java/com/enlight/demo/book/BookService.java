package com.enlight.demo.book;

import com.enlight.demo.common.DemoNotFoundException;
import com.enlight.demo.common.DemoValidationException;
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

    /**
     * Removes one book row.
     */
    public void deleteBook(Long id) {
        if (id == null) {
            throw new DemoValidationException("Book id is required");
        }

        boolean deleted = repository.deleteById(id);
        if (!deleted) {
            throw new DemoNotFoundException("Book id " + id + " was not found");
        }
    }

    /**
     * Finds all books where author contains the provided text (case-insensitive).
     */
    public List<Book> searchBooksByAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new DemoValidationException("author query parameter is required");
        }

        return repository.findByAuthor(author);
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
        if (request.getCategory() == null || request.getCategory().isBlank()) {
            throw new DemoValidationException("Book category is required");
        }
    }
}

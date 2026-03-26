package com.enlight.demo.book;

import com.enlight.demo.common.DemoDatabaseException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

/**
 * JDBC repository for the Book demo.
 * This keeps the flow explicit:
 * incoming request -> SQL query -> response DTO.
 */
@ApplicationScoped
public class BookRepository {

    @Inject
    DataSource dataSource;

    /**
     * Returns all books sorted by ID so workshop responses are stable and readable.
     */
    public List<Book> findAll() {
        String sql = "SELECT ID, TITLE, AUTHOR, ISBN, CATEGORY FROM BOOK ORDER BY ID";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(map(resultSet));
            }
            return books;
        } catch (SQLException exception) {
            throw new DemoDatabaseException("Failed to load books", exception);
        }
    }

    /**
     * Returns one book by ID, or empty if it does not exist.
     */
    public Optional<Book> findById(Long id) {
        String sql = "SELECT ID, TITLE, AUTHOR, ISBN, CATEGORY FROM BOOK WHERE ID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return Optional.of(map(resultSet));
            }
        } catch (SQLException exception) {
            throw new DemoDatabaseException("Failed to find book by id: " + id, exception);
        }
    }

    /**
     * Inserts one row and returns the generated ID from Oracle.
     */
    public Book save(Book book) {
        String insertSql = "INSERT INTO BOOK (TITLE, AUTHOR, ISBN, CATEGORY) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     insertSql,
                     new String[] {"ID"}
             )) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setString(4, book.getCategory());
            statement.executeUpdate();

            // Oracle returns generated columns when we ask for the ID column name.
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new DemoDatabaseException(
                            "Insert succeeded but generated ID was not returned",
                            new IllegalStateException("Database did not return generated key columns")
                    );
                }
                long id = generatedKeys.getLong(1);
                return new Book(id, book.getTitle(), book.getAuthor(), book.getIsbn(), book.getCategory());
            }
        } catch (SQLException exception) {
            throw new DemoDatabaseException("Failed to save book", exception);
        }
    }

    private Book map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        String title = resultSet.getString("TITLE");
        String author = resultSet.getString("AUTHOR");
        String isbn = resultSet.getString("ISBN");
        String category = resultSet.getString("CATEGORY");
        return new Book(id, title, author, isbn, category);
    }

    /**
     * Deletes one book by ID.
     * Returns true if a row was removed, false when the ID did not exist.
     */
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM BOOK WHERE ID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException exception) {
            throw new DemoDatabaseException("Failed to delete book with id: " + id, exception);
        }
    }

    /**
     * Finds books where author contains the search term, case-insensitive.
     */
    public List<Book> findByAuthor(String author) {
        String sql = "SELECT ID, TITLE, AUTHOR, ISBN, CATEGORY FROM BOOK WHERE UPPER(AUTHOR) LIKE UPPER(?) ORDER BY ID";
        String searchPattern = "%" + author.trim() + "%";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, searchPattern);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Book> books = new ArrayList<>();
                while (resultSet.next()) {
                    books.add(map(resultSet));
                }
                return books;
            }
        } catch (SQLException exception) {
            throw new DemoDatabaseException("Failed to search books by author: " + author, exception);
        }
    }
}

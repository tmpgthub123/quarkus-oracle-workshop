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
        String sql = "SELECT ID, TITLE, AUTHOR, ISBN FROM BOOK ORDER BY ID";

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
        String sql = "SELECT ID, TITLE, AUTHOR, ISBN FROM BOOK WHERE ID = ?";

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
        String insertSql = "INSERT INTO BOOK (TITLE, AUTHOR, ISBN) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     insertSql,
                     new String[] {"ID"}
             )) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
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
                return new Book(id, book.getTitle(), book.getAuthor(), book.getIsbn());
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
        return new Book(id, title, author, isbn);
    }

    // TODO: Workshop task 1
    // Implement SQL: DELETE FROM BOOK WHERE ID = ?
    // Return true if a row was deleted, false if id was not found.
    public boolean deleteById(Long id) {
        throw new UnsupportedOperationException("TODO: Implement Workshop task 1");
    }

    // TODO: Workshop task 3
    // Implement SQL with UPPER(AUTHOR) LIKE UPPER(?) and ORDER BY ID.
    public List<Book> findByAuthor(String author) {
        throw new UnsupportedOperationException("TODO: Implement Workshop task 3");
    }
}

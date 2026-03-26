-- Insert sample BOOK rows only when they are not already present.
-- Keeping this idempotent makes repeated starts safe.

BEGIN
    INSERT INTO BOOK (TITLE, AUTHOR, ISBN, CATEGORY)
    SELECT 'Clean Code', 'Robert C. Martin', '978-0132350884', 'Programming'
      FROM dual
     WHERE NOT EXISTS (SELECT 1 FROM BOOK WHERE ISBN = '978-0132350884');

    INSERT INTO BOOK (TITLE, AUTHOR, ISBN, CATEGORY)
    SELECT 'Refactoring', 'Martin Fowler', '978-0134757599', 'Software Design'
      FROM dual
     WHERE NOT EXISTS (SELECT 1 FROM BOOK WHERE ISBN = '978-0134757599');

    INSERT INTO BOOK (TITLE, AUTHOR, ISBN, CATEGORY)
    SELECT 'The Pragmatic Programmer', 'Andrew Hunt, David Thomas', '978-0201616224', 'Programming'
      FROM dual
     WHERE NOT EXISTS (SELECT 1 FROM BOOK WHERE ISBN = '978-0201616224');
END;

-- Insert sample BOOK rows only when they are not already present.
-- Keeping this idempotent makes repeated starts safe.
-- TODO: Workshop task 2
-- Add CATEGORY column usage here (column + values) once the model/repository are extended.

BEGIN
    INSERT INTO BOOK (TITLE, AUTHOR, ISBN)
    SELECT 'Clean Code', 'Robert C. Martin', '978-0132350884'
      FROM dual
     WHERE NOT EXISTS (SELECT 1 FROM BOOK WHERE ISBN = '978-0132350884');

    INSERT INTO BOOK (TITLE, AUTHOR, ISBN)
    SELECT 'Refactoring', 'Martin Fowler', '978-0134757599'
      FROM dual
     WHERE NOT EXISTS (SELECT 1 FROM BOOK WHERE ISBN = '978-0134757599');

    INSERT INTO BOOK (TITLE, AUTHOR, ISBN)
    SELECT 'The Pragmatic Programmer', 'Andrew Hunt, David Thomas', '978-0201616224'
      FROM dual
     WHERE NOT EXISTS (SELECT 1 FROM BOOK WHERE ISBN = '978-0201616224');
END;

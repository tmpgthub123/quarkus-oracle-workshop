-- Seed data for local manual SQL runs.
-- This is safe to run multiple times because inserts happen only when ISBN is missing.
-- TODO: Workshop task 2
-- Add CATEGORY column usage (column name and values) once task 2 is implemented.

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

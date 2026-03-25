# ZADACI – Quarkus Book Workshop Starter

Ovaj projekat je starter verzija za vežbu.  
Baza i osnovni `Book` endpointovi (`GET /api/books`, `GET /api/books/{id}`, `POST /api/books`) rade.

## Task 1: Implementiraj DELETE /api/books/{id}

**Cilj:** Omogućiti brisanje knjige po ID-u.

**Fajlovi za izmenu:**
- `src/main/java/com/enlight/demo/book/BookResource.java`
- `src/main/java/com/enlight/demo/book/BookService.java`
- `src/main/java/com/enlight/demo/book/BookRepository.java`

**Hintovi:**
- U `BookResource` dodaj `@DELETE @Path("/{id:\\d+}")`.
- U `BookService` validiraj da `id != null` i `repository.deleteById(id)`.
- Ako nije obrisano, vrati/throw `DemoNotFoundException`.
- U `BookRepository` implementiraj SQL:
  - `DELETE FROM BOOK WHERE ID = ?`

**Očekivano ponašanje:**
- `204 No Content` kada je red obrisan.
- `404 Not Found` kada ID ne postoji.

**Primer request-a:**
```http
DELETE http://localhost:8080/api/books/3
```

---

## Task 2: Dodaj `category` u Book flow

**Cilj:** Proširiti `Book` model i ceo flow da nosi kategoriju knjige.

**Fajlovi za izmenu:**
- `src/main/java/com/enlight/demo/book/Book.java`
- `src/main/resources/sql/01_book_schema.sql`
- `src/main/resources/sql/02_book_data.sql`
- `sql/schema.sql`
- `sql/data.sql`
- `src/main/java/com/enlight/demo/book/BookRepository.java`
- `requests/book-create.http`

**Hintovi:**
- U model dodaj `category` i getter/setter.
- U SQL šemi dodaj kolonu `CATEGORY`.
- `INSERT` i `SELECT` upite proširi na 4 kolone (`TITLE, AUTHOR, ISBN, CATEGORY`).
- U mapiranju i validaciji proveri polje.

**Očekivano ponašanje:**
- POST body može da prihvati/vrati `category`.
- GET list i GET by id vraćaju `category`.

**Primer request-a nakon implementacije:**
```json
{
  "title": "Effective Java",
  "author": "Joshua Bloch",
  "isbn": "9780134685991",
  "category": "Java"
}
```

---

## Task 3: Implementiraj GET /api/books/search?author=...

**Cilj:** Omogućiti pretragu knjiga po delu imena autora.

**Fajlovi za izmenu:**
- `src/main/java/com/enlight/demo/book/BookResource.java`
- `src/main/java/com/enlight/demo/book/BookService.java`
- `src/main/java/com/enlight/demo/book/BookRepository.java`

**Hintovi:**
- U `BookResource` dodaj endpoint sa `@GET` i `@Path("/search")`.
- U service-u validiraj query param `author`.
- U repository-u SQL treba da bude:
  - `SELECT ... FROM BOOK WHERE UPPER(AUTHOR) LIKE UPPER(?) ORDER BY ID`

**Očekivano ponašanje:**
- Za `author=Bloch` vraća sve naslove čiji autor sadrži `Bloch` (neosetljivo na case).

**Primer request-a:**
```http
GET http://localhost:8080/api/books/search?author=Bloch
```


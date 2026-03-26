package com.enlight.demo.book;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Exposes simple CRUD-like REST endpoints for the workshop intro.
 */
@Path("/api/books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    BookService service;

    @GET
    public List<Book> getAll() {
        return service.listBooks();
    }

    @GET
    @Path("/{id:\\d+}")
    public Book getById(@PathParam("id") Long id) {
        return service.getBookById(id);
    }

    @POST
    public Response create(Book request) {
        Book created = service.createBook(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    /**
     * Deletes one book by ID.
     * - 204 when the row is deleted
     * - 404 when the book is not found
     */
    @DELETE
    @Path("/{id:\\d+}")
    public Response deleteById(@PathParam("id") Long id) {
        service.deleteBook(id);
        return Response.noContent().build();
    }

    /**
     * Searches books by author.
     * Example: /api/books/search?author=Bloch
     */
    @GET
    @Path("/search")
    public List<Book> searchByAuthor(@QueryParam("author") String author) {
        return service.searchBooksByAuthor(author);
    }
}

package com.enlight.demo.book;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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

    // TODO: Workshop task 1
    // Add DELETE /api/books/{id} endpoint.
    // Return:
    // - 204 when deleted
    // - 404 when not found
    // and call service.deleteBook(id).

    // TODO: Workshop task 3
    // Add GET /api/books/search?author=... endpoint.
    // Use query param `author`, then return service.searchBooksByAuthor(author).
}

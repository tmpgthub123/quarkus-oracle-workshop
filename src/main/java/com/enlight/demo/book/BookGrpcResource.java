package com.enlight.demo.book;

import com.enlight.demo.book.grpc.BookReply;
import com.enlight.demo.book.grpc.BookServiceGrpc;
import com.enlight.demo.book.grpc.GetBookByIdRequest;
import com.enlight.demo.common.DemoNotFoundException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.quarkus.grpc.GrpcClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Bonus resource that shows a simple REST -> gRPC internal flow.
 * <p>
 * This keeps REST endpoint for workshop participants, while the transport is gRPC:
 * - HTTP request comes in here.
 * - gRPC client calls BookService.GetBookById.
 * - Response is mapped back to the same Book domain.
 */
@Path("/api/books/grpc")
@Produces(MediaType.APPLICATION_JSON)
public class BookGrpcResource {

    @Inject
    @GrpcClient("book-service")
    BookServiceGrpc.BookServiceBlockingStub grpcClient;

    @GET
    @Path("/{id:\\d+}")
    public Book getById(@PathParam("id") Long id) {
        try {
            BookReply reply = grpcClient.getBookById(
                    GetBookByIdRequest.newBuilder()
                            .setId(id)
                            .build()
            );
            return new Book(
                    reply.getId(),
                    reply.getTitle(),
                    reply.getAuthor(),
                    reply.getIsbn(),
                    reply.getCategory()
            );
        } catch (StatusRuntimeException error) {
            if (error.getStatus().getCode() == Status.NOT_FOUND.getCode()) {
                throw new DemoNotFoundException(error.getStatus().getDescription());
            }
            throw error;
        }
    }
}

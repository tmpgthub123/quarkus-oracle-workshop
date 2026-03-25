package com.enlight.demo.book;

import com.enlight.demo.book.grpc.BookReply;
import com.enlight.demo.book.grpc.BookServiceGrpc;
import com.enlight.demo.book.grpc.GetBookByIdRequest;
import com.enlight.demo.common.DemoNotFoundException;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Inject;

/**
 * Bonus gRPC demo service.
 *
 * - REST and gRPC are both exposed, so no existing endpoint is replaced.
 * - This is intentionally small and mirrors REST conceptually:
 *   one request with one ID -> one response with one Book.
 */
@GrpcService
public class BookGrpcService extends BookServiceGrpc.BookServiceImplBase {

    @Inject
    BookService bookService;

    /**
     * Unary gRPC method.
     * Receives a book id, reuses existing BookService logic, and returns one BookReply.
     */
    @Override
    public void getBookById(GetBookByIdRequest request, StreamObserver<BookReply> responseObserver) {
        try {
            Book foundBook = bookService.getBookById(request.getId());
            responseObserver.onNext(toBookReply(foundBook));
            responseObserver.onCompleted();
        } catch (DemoNotFoundException notFound) {
            // gRPC-style "not found" response.
            responseObserver.onError(Status.NOT_FOUND.withDescription(notFound.getMessage()).asRuntimeException());
        }
    }

    /**
     * Converts the shared domain model into a gRPC response message.
     */
    private BookReply toBookReply(Book book) {
        return BookReply.newBuilder()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setIsbn(book.getIsbn())
                .build();
    }
}

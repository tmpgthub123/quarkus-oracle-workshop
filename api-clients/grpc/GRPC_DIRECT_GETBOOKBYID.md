# Direct gRPC helper

This is a plain reference for manual Insomnia setup.
It is intentionally not stored as an Insomnia import file, so `api-clients/insomnia/`
can contain only real importable Insomnia resources.

- Server: `localhost:9000`
- Proto: `src/main/proto/book_service.proto`
- Package: `com.enlight.demo.book`
- Service: `BookService`
- Method: `GetBookById`

Request body:

```json
{
  "id": 1
}
```

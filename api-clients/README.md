# API Clients (Workshop Imports)

This folder contains ready-to-import collections for the existing workshop endpoints.

Included files:
- `insomnia/quarkus-oracle-workshop-insomnia.json`
- `grpc/GRPC_DIRECT_GETBOOKBYID.md`

The imported Insomnia collection contains these requests:
- `GET /api/books`
- `GET /api/books/{id}`
- `POST /api/books`
- `DELETE /api/books/{id}`
- `GET /api/books/search?author=...`
- `GET /api/books/grpc/{id}` (REST wrapper around the gRPC demo)
- `BookService/GetBookById` (direct gRPC request wired to relative proto path `src/main/proto/book_service.proto`)

Shared variable (Insomnia uses this):
- `baseUrl = http://localhost:8080`
- `grpcUrl = localhost:9000`
- `bookId = 1`

The direct gRPC request in the imported collection uses an Insomnia proto tree that resolves to:

- `src/main/proto/book_service.proto`

That keeps the import portable across machines instead of relying on an absolute local path.

Fallback helper (`grpc/GRPC_DIRECT_GETBOOKBYID.md`) is still included as a plain reference with the same settings:
- Server: `localhost:9000`
- Service: `BookService`
- Method: `GetBookById`
- Request body:

```json
{
  "id": 1
}
```

## Import to Insomnia

1. Open Insomnia.
2. Click **Import**.
3. Choose **File** and select `insomnia/quarkus-oracle-workshop-insomnia.json`.
4. Select the imported workspace.
5. In the imported workspace, set **Environment** to `Workshop Environment` if needed.
6. Verify the first request points to `{{ _.baseUrl }}` and baseUrl is `http://localhost:8080`.
7. The direct gRPC request is available under `Book Endpoints (direct gRPC)`.
8. If you open the proto selector in that request, you should see the imported tree ending in `src/main/proto/book_service.proto`.

`api-clients/insomnia/` now contains only real Insomnia import resources, so folder import should no longer show a warning for the helper file.

## Notes

- Request bodies and URLs are copied exactly from the project’s request files and implemented endpoints.
- Keep the same JSON payload keys:
  - `title`, `author`, `isbn`, `category`

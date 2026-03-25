# API Clients (Workshop Imports)

This folder contains ready-to-import collections for the existing workshop endpoints.

Included clients:
- `insomnia/quarkus-oracle-workshop-insomnia.json`
- `kreya/quarkus-oracle-workshop-kreya.json`

Both files contain these requests (matching current starter Quarkus endpoints):
- `GET /api/books`
- `GET /api/books/{id}`
- `POST /api/books`

Shared variable (Insomnia uses this):
- `baseUrl = http://localhost:8080`

For the starter workshop tasks, see [ZADACI.md](../ZADACI.md):
- Task 1: `DELETE /api/books/{id}`
- Task 2: add `category` in Book flow
- Task 3: `GET /api/books/search?author=...`

## Import to Insomnia

1. Open Insomnia.
2. Click **Import**.
3. Choose **File** and select `insomnia/quarkus-oracle-workshop-insomnia.json`.
4. Select the imported workspace.
5. In the imported workspace, set **Environment** to `Workshop Environment` if needed.
6. Verify the first request points to `{{ _.baseUrl }}` and baseUrl is `http://localhost:8080`.

## Import to Kreya

Option A (recommended):
1. Open Kreya.
2. Go to **Import**.
3. If your Kreya version asks for importer type, choose **Postman Collection**.
4. Select `kreya/quarkus-oracle-workshop-kreya.json`.
5. Confirm the import created:
   - Book Endpoints (REST Intro)
6. Send a request directly:
   - `GET http://localhost:8080/api/books`.
7. If you still see environment/variable errors, use `requests/*.http` files or Insomnia import as a fallback while we keep the Kreya import as minimal.

Option B (if your Kreya version supports Insomnia import):
1. Open Kreya.
2. Use **Import** and choose **Insomnia v4**.
3. Select `insomnia/quarkus-oracle-workshop-insomnia.json`.
4. Use the resulting operation list.

## Notes

- Request bodies and URLs are copied exactly from the project’s request files and implemented endpoints.
- Keep the same JSON payload keys:
  - `title`, `author`, `isbn`

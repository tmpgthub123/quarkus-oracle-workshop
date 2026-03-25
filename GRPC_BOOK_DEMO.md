# GRPC bonus demo (književni primer)

Ovaj deo je **bonus demo** za radionicu o REST-u.  
REST ostaje glavna tema, a gRPC je samo dodatak da se vidi kako se isti Book model može čitati i preko druge komunikacije.

## Šta je gRPC?

gRPC je način da klijent i server pričaju preko jedne tačno definisane šeme.
Jednostavnije rečeno:

- REST: klijent zove HTTP URL i dobija/šalje JSON.
- gRPC: klijent poziva metodu servisa (kao funkciju), i ta metoda ima tačno definisane ulazne i izlazne poruke.

## Kako se razlikuje od REST-a?

- REST endpoint je obično URL kao `GET /api/books/1`.
- gRPC poziv je `BookService -> GetBookById(request)` sa strukturisanim zahtevom.
- REST koristi uglavnom JSON.
- gRPC koristi Protobuf (binary format) za bržu i strožiju razmenu.

bitno: **nema više rute kroz URI za svaki use-case**, već jedan servis + metode.

## Šta je .proto fajl?

`.proto` fajl je ugovor između klijenta i servera.

U ovom projektu je to: `src/main/proto/book_service.proto`

U fajlu su definisani:

- `service`: `BookService`
- zahtev: `GetBookByIdRequest`
- odgovor: `BookReply`

Kad pokrenemo aplikaciju, Quarkus iz ovog `.proto` fajla generiše delove Java koda koje servis koristi.

## Šta radi naš primer?

Imamo tačno jedan gRPC metod:

- `GetBookById`

Tok je vrlo jednostavan:

1. Klijent pošalje `id` u `GetBookByIdRequest`.
2. gRPC servis u serveru pozove postojeći `BookService.getBookById(id)`.
3. Ako knjiga postoji, vraća `BookReply` (`id`, `title`, `author`, `isbn`).
4. Ako ne postoji, vraća se gRPC error `NOT_FOUND`.

## Važne datoteke

- `.proto`: `src/main/proto/book_service.proto`
- gRPC implementacija: `src/main/java/com/enlight/demo/book/BookGrpcService.java`
- REST Book primer ostaje: `src/main/java/com/enlight/demo/book/BookResource.java`
- Podešavanje portova: `src/main/resources/application.properties`
  - REST: `8080`
  - gRPC: `9000`

## Kako testirati iz Kreya / Insomnia

Postoje dva jednostavna načina za test:

- Direktno gRPC (preporučeno za bonus deo), koristeći `localhost:9000`.
  - U Kreya možeš importovati/kreirati request iz:
    - `api-clients/kreya/Quarkus Oracle Workshop/Book Endpoints (direct gRPC)/GetBookById.krop`
  - Za Insomnia je u ovoj verziji helper fajl:
    - `api-clients/insomnia/GRPC_DIRECT_GETBOOKBYID.json`
    (koristi ga kao ručni šablon: server, service i payload),
- Kratki REST wrapper endpoint (ako želite da testirate kroz isti alat koji već znate):
  `GET /api/books/grpc/{id}`.

Ako već koristiš REST klijent (Insomnia/Kreya), možeš prvo koristiti wrapper endpoint:

- Host: `localhost:8080`
- Path: `/api/books/grpc/1`
- Metoda: `GET`

Za direktni gRPC test:

- Host: `localhost`
- gRPC port: `9000`
- Service: `BookService`
- Method: `GetBookById`

Primer `GetBookByIdRequest`:

```json
  {
  "id": 1
}
```

Ako vrati grešku, očekivana je:
- `NOT_FOUND` kada id ne postoji u tabeli `BOOK`.

## Koji je tok poziva?

`Kreya/Insomnia -> gRPC channel (localhost:9000) -> BookService.GetBookById -> BookGrpcService -> BookService (REST/domain layer) -> BookRepository -> Oracle`

Ovaj tok pomaže da vidite istu logiku, samo “u drugom stilu komunikacije”.

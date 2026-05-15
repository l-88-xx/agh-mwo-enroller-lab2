# Enroller REST API

Aplikacja REST API do zarządzania uczestnikami i spotkaniami. 

Technologie: Spring Boot, Hibernate, SQLite.

## Funkcjonalności

- zarządzanie uczestnikami
- zarządzanie spotkaniami
- przypisywanie uczestników do spotkań
- filtrowanie i sortowanie uczestników
- walidacja danych wejściowych
- autoryzacja JWT
- szyfrowanie haseł BCrypt
- REST API zgodne z HTTP status codes

# Uruchomienie projektu

mvn spring-boot:run

Aplikacja uruchomi się domyślnie pod adresem: http://localhost:8080


# Autoryzacja JWT

## Użycie tokenu

Do chronionych endpointów konieczny jest nagłówek: Authorization: Bearer JWT_TOKEN


# Endpointy API

## Participants

### Pobranie wszystkich uczestników: GET/participants
### Sortowanie i filtrowanie: GET/participants?sortBy=login&sortOrder=ASC&key=jo
### Pobranie uczestnika: GET/participants/{login}
### Dodanie uczestnika: POST/participants
### Aktualizacja uczestnika: PUT/participants/{login}
### Usunięcie uczestnika: DELETE/participants/{login}


# Meetings

### Pobranie wszystkich spotkań: GET/meetings
### Pobranie spotkania: GET/meetings/{id}
### Dodanie spotkania: POST/meetings
### Aktualizacja spotkania: PUT/meetings/{id}
### Usunięcie spotkania: DELETE/meetings/{id}


# Zarządzanie uczestnikami spotkań

### Pobranie uczestników spotkania: GET/meetings/{id}/participants
### Dodanie uczestnika do spotkania: POST/meetings/{id}/participants
### Usunięcie uczestnika ze spotkania: DELETE/meetings/{id}/participants/{login}

# Walidacje

## Login
- wymagany
- długość: 3–15 znaków

## Hasło
- wymagane
- długość: 4–20 znaków

# Statusy HTTP

Aplikacja wykorzystuje standardowe statusy HTTP:

- `200 OK`
- `201 CREATED`
- `204 NO CONTENT`
- `400 BAD REQUEST`
- `404 NOT FOUND`
- `409 CONFLICT`

# Baza danych

Projekt wykorzystuje bazę SQLite:

```text
enroller.db
```

Konfiguracja Hibernate znajduje się w:

```text
hibernate.cfg.xml
```
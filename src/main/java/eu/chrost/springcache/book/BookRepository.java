package eu.chrost.springcache.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
class BookRepository {
    public Book loadByIsbn(String isbn) {
        log.info(">>> Repository: loading isbn {}", isbn);
        return new Book(isbn, "Title-" + isbn, "Author-" + isbn, 2024);
    }

    public Book loadByIsbnAndYear(String isbn, int year) {
        log.info(">>> Repository: loading isbn+year {}, {}", isbn, year);
        return new Book(isbn, "Title-" + isbn, "Author-" + isbn, year);
    }
}

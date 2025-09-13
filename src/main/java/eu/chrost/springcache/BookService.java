package eu.chrost.springcache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class BookService {
    /**
     * Default cache using method argument as key (ISBN).
     */
    @Cacheable(cacheNames = "books")
    public Book findByIsbn(String isbn) {
        System.out.println(">>> Executing findByIsbn for " + isbn);
        return new Book(isbn, "Title-" + isbn, "Author-" + isbn, 2024);
    }

    /**
     * Cache with explicit key using SpEL (multi-column: isbn + year).
     */
    @Cacheable(cacheNames = "booksByIsbnYear", key = "#isbn + '-' + #year")
    public Book findByIsbnAndYear(String isbn, int year) {
        System.out.println(">>> Executing findByIsbnAndYear for " + isbn + ", " + year);
        return new Book(isbn, "Title-" + isbn, "Author-" + isbn, year);
    }

    /**
     * Cache using custom KeyGenerator bean.
     */
    @Cacheable(cacheNames = "booksCustomKey", keyGenerator = "isbnYearKeyGenerator")
    public Book findWithCustomKey(String isbn, int year) {
        System.out.println(">>> Executing findWithCustomKey for " + isbn + ", " + year);
        return new Book(isbn, "Title-" + isbn, "Author-" + isbn, year);
    }

    /**
     * Evict a single cached item (by key).
     */
    @CacheEvict(cacheNames = "books", key = "#isbn")
    public void evictByIsbn(String isbn) {
        System.out.println(">>> Evicting cache entry for isbn=" + isbn);
    }

    /**
     * Evict the whole cache region.
     */
    @CacheEvict(cacheNames = "books", allEntries = true)
    public void evictAll() {
        System.out.println(">>> Evicting all cache entries for 'books'");
    }
}

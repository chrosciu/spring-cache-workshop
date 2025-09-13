package eu.chrost.springcache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class BookService {
    private final BookRepository bookRepository;

    /**
     * Default cache using method argument as key (ISBN).
     */
    @Cacheable(cacheNames = "books")
    public Book findByIsbn(String isbn) {
        log.info(">>> Executing findByIsbn for {}", isbn);
        return bookRepository.loadByIsbn(isbn);
    }

    /**
     * Cache with explicit key using SpEL (multi-column: isbn + year).
     */
    @Cacheable(cacheNames = "booksByIsbnYear", key = "#isbn + '-' + #year")
    public Book findByIsbnAndYear(String isbn, int year) {
        log.info(">>> Executing findByIsbnAndYear for {}, {}", isbn, year);
        return bookRepository.loadByIsbnAndYear(isbn, year);
    }

    /**
     * Cache using custom KeyGenerator bean.
     */
    @Cacheable(cacheNames = "booksCustomKey", keyGenerator = "isbnYearKeyGenerator")
    public Book findWithCustomKey(String isbn, int year) {
        log.info(">>> Executing findWithCustomKey for {}, {}", isbn, year);
        return bookRepository.loadByIsbnAndYear(isbn, year);
    }

    /**
     * Evict a single cached item (by key).
     */
    @CacheEvict(cacheNames = "books", key = "#isbn")
    public void evictByIsbn(String isbn) {
        log.info(">>> Evicting cache entry for isbn={}", isbn);
    }

    /**
     * Evict the whole cache region.
     */
    @CacheEvict(cacheNames = "books", allEntries = true)
    public void evictAll() {
        log.info(">>> Evicting all cache entries for 'books'");
    }
}

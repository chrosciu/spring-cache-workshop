package eu.chrost.springcache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;

class BookServiceCacheIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void testCacheable_defaultKey() {
        // first call → executes method
        Book book1 = bookService.findByIsbn("111");

        // second call with same key → should return cached value
        Book book2 = bookService.findByIsbn("111");

        // object should be same (cache hit)
        assertThat(book2).isSameAs(book1);

        // cache should contain the entry
        assertThat(cacheManager.getCache("books").get("111").get()).isSameAs(book1);
    }

    @Test
    void testCacheEvict_singleEntry() {
        Book book = bookService.findByIsbn("222");

        // ensure it's cached
        assertThat(cacheManager.getCache("books").get("222")).isNotNull();

        // evict only "222"
        bookService.evictByIsbn("222");

        // entry should be gone
        assertThat(cacheManager.getCache("books").get("222")).isNull();
    }

    @Test
    void testCacheEvict_allEntries() {
        bookService.findByIsbn("333");
        bookService.findByIsbn("444");

        // ensure both cached
        assertThat(cacheManager.getCache("books").get("333")).isNotNull();
        assertThat(cacheManager.getCache("books").get("444")).isNotNull();

        // evict all
        bookService.evictAll();

        assertThat(cacheManager.getCache("books").get("333")).isNull();
        assertThat(cacheManager.getCache("books").get("444")).isNull();
    }

    @Test
    void testCacheable_withSpELKey() {
        Book b1 = bookService.findByIsbnAndYear("555", 2020);
        Book b2 = bookService.findByIsbnAndYear("555", 2020);

        assertThat(b2).isSameAs(b1);

        // key is "isbn-year"
        assertThat(cacheManager.getCache("booksByIsbnYear").get("555-2020").get())
                .isSameAs(b1);
    }

    @Test
    void testCacheable_withCustomKeyGenerator() {
        Book b1 = bookService.findWithCustomKey("666", 2021);
        Book b2 = bookService.findWithCustomKey("666", 2021);

        assertThat(b2).isSameAs(b1);

        // key is "isbn-year" because of custom KeyGenerator
        assertThat(cacheManager.getCache("booksCustomKey").get("666-2021").get())
                .isSameAs(b1);
    }
}


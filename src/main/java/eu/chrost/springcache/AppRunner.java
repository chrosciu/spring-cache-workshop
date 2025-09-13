package eu.chrost.springcache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@RequiredArgsConstructor
@Slf4j
class AppRunner implements CommandLineRunner {
    private final BookService bookService;
    private final CacheInspector cacheInspector;

	public static void main(String[] args) {
		SpringApplication.run(AppRunner.class, args);
	}

    @Override
    public void run(String... args) {
        // Cache with default key (isbn)
        cacheInspector.logCache("books");
        log.info("First call (isbn=111): {}", bookService.findByIsbn("111"));
        cacheInspector.logCache("books");
        log.info("Second call (isbn=111): {}", bookService.findByIsbn("111")); // cache hit

        // Evict single item
        bookService.evictByIsbn("111");
        cacheInspector.logCache("books");
        log.info("Third call (isbn=111 after eviction): {}", bookService.findByIsbn("111")); // recomputed
        cacheInspector.logCache("books");

        // Cache with SpEL multi-key
        log.info("{}", bookService.findByIsbnAndYear("222", 2020));
        log.info("{}", bookService.findByIsbnAndYear("222", 2020)); // cache hit

        // Cache with custom KeyGenerator
        log.info("{}", bookService.findWithCustomKey("333", 2021));
        log.info("{}", bookService.findWithCustomKey("333", 2021)); // cache hit

        log.info("First call (isbn=222): {}", bookService.findByIsbn("222"));
        cacheInspector.logCache("books");
        // Evict whole region
        bookService.evictAll();
        cacheInspector.logCache("books");
        log.info("After evictAll, reloading isbn=111: {}", bookService.findByIsbn("111"));
    }
}

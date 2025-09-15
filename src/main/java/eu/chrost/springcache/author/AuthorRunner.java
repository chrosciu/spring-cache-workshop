package eu.chrost.springcache.author;

import eu.chrost.springcache.common.CacheInspector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBooleanProperty("app.runner.enabled")
@RequiredArgsConstructor
@Slf4j
class AuthorRunner implements CommandLineRunner {
    private final AuthorService authorService;
    private final CacheInspector cacheInspector;

    @Override
    public void run(String... args) {
        // Task 1: basic caching
        log.info("1) Calling getAuthorById(1) twice...");
        authorService.getAuthorById(1);
        authorService.getAuthorById(1); // should hit cache
        cacheInspector.logCache("authors");

        // Task 2: evict single entry
        log.info("2) Evicting author with id=1...");
        authorService.deleteAuthor(1);
        cacheInspector.logCache("authors");
        authorService.getAuthorById(1); // should hit repo again
        cacheInspector.logCache("authors");

        // Task 3: evict all entries
        log.info("3) Adding two authors to cache, then clearing...");
        authorService.getAuthorById(2);
        authorService.getAuthorById(3);
        cacheInspector.logCache("authors");

        authorService.clearAuthorsCache();
        cacheInspector.logCache("authors");

        // Task 4: composite key
        log.info("4) Using composite key (name+country)...");
        authorService.findAuthorByNameAndCountry("George Orwell", "UK");
        authorService.findAuthorByNameAndCountry("George Orwell", "UK"); // cached
        authorService.findAuthorByNameAndCountry("Mark Twain", "USA");   // new entry
        cacheInspector.logCache("authors");

        // Task 5: custom key generator
        log.info("5) Using custom key generator...");
        authorService.getAuthorWithCustomKey(2);
        authorService.getAuthorWithCustomKey(2); // cached with custom key
        cacheInspector.logCache("authors");
    }
}


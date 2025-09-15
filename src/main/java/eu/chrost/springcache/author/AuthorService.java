package eu.chrost.springcache.author;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {
    private final AuthorRepository repository;

    // Task 1: basic @Cacheable
    @Cacheable("authors")
    public Author getAuthorById(int id) {
        return repository.findById(id);
    }

    // Task 2: evict single entry
    @CacheEvict(cacheNames = "authors", key = "#id")
    public void deleteAuthor(int id) {
        log.info("Evicting author with id: {}", id);
    }

    // Task 3: evict all entries
    @CacheEvict(cacheNames = "authors", allEntries = true)
    public void clearAuthorsCache() {
        log.info("Clearing all authors from cache");
    }

    // Task 4: composite key
    @Cacheable(cacheNames = "authors", key = "#name + '-' + #country")
    public Author findAuthorByNameAndCountry(String name, String country) {
        return repository.findByNameAndCountry(name, country);
    }

    // Task 5: custom key generator
    @Cacheable(cacheNames = "authors", keyGenerator = "customAuthorKeyGenerator")
    public Author getAuthorWithCustomKey(int id) {
        return repository.findById(id);
    }
}

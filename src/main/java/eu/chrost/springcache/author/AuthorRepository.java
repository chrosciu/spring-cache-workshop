package eu.chrost.springcache.author;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Slf4j
class AuthorRepository {
    private final Map<Integer, Author> authors = Map.of(
            1, new Author(1, "George Orwell", "UK"),
            2, new Author(2, "Mark Twain", "USA"),
            3, new Author(3, "Franz Kafka", "Czech Republic")
    );

    public Author findById(int id) {
        log.info("Repository called for id: {}", id);
        return authors.get(id);
    }

    public Author findByNameAndCountry(String name, String country) {
        log.info("Repository called for name: {} and country: {}", name,  country);
        return authors.values().stream()
                .filter(author -> author.name().equals(name) && author.country().equals(country))
                .findFirst()
                .orElse(null);
    }
}

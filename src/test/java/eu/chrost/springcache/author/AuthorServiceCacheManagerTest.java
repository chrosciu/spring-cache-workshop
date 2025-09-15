package eu.chrost.springcache.author;

import eu.chrost.springcache.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorServiceCacheManagerTest extends BaseIntegrationTest {
    @Autowired
    private AuthorService service;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void entryShouldBeStoredInCache() {
        // when
        Author author = service.getAuthorById(1);

        // then - check cache directly
        Cache cache = cacheManager.getCache("authors");
        assertThat(cache).isNotNull();

        Author cachedAuthor = cache.get(1, Author.class);
        assertThat(cachedAuthor).isEqualTo(author);
    }

    @Test
    void entryShouldBeEvictedFromCache() {
        // given
        service.getAuthorById(2);
        Cache cache = cacheManager.getCache("authors");
        assertThat(cache).isNotNull();
        assertThat(cache.get(2, Author.class)).isNotNull();

        // when
        service.deleteAuthor(2);

        // then
        assertThat(cache.get(2, Author.class)).isNull();
    }
}

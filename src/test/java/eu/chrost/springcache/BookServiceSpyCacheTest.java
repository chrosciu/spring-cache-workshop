package eu.chrost.springcache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BookServiceSpyCacheTest extends BaseIntegrationTest {

    @Autowired
    private BookService bookService;

    @MockitoSpyBean
    private BookRepository repository;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void testCacheable_methodCalledOnlyOnce_whenSpyingRepository() {
        // first call -> repository actually executed
        Book book1 = bookService.findByIsbn("111");

        // second call -> should be returned from cache; repository should not be executed again
        Book book2 = bookService.findByIsbn("111");

        // same instance returned from cache
        assertThat(book2).isSameAs(book1);

        // repository.loadByIsbn must be invoked only once
        verify(repository, times(1)).loadByIsbn("111");

        // direct cache state assertion (optional)
        assertThat(cacheManager.getCache("books").get("111").get()).isSameAs(book1);
    }
}

package eu.chrost.springcache.author;

import eu.chrost.springcache.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AuthorServiceCachingTest extends BaseIntegrationTest {
    @MockitoSpyBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Test
    void cacheShouldAvoidSecondRepositoryCall() {
        authorService.getAuthorById(1);
        authorService.getAuthorById(1);

        // repo should be called only once
        verify(authorRepository, times(1)).findById(1);
    }

    @Test
    void evictionShouldForceRepositoryCallAgain() {
        authorService.getAuthorById(2);
        authorService.clearAuthorsCache();
        authorService.getAuthorById(2);

        // repo should be called twice (before + after eviction)
        verify(authorRepository, times(2)).findById(2);
    }
}

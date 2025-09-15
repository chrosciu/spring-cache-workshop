# 📝 Tasks: Spring Caching (Simple Cache)

## **Task 1 — Create your own domain**

* Define a new record, `Author` with fields `id`, `name`, `country`.
* Create an `AuthorRepository` (e.g. `findById(int id)` returning dummy data).
* Create an `AuthorService` that delegates to the repository.
* Add a method `getAuthorById(int id)` and annotate it with `@Cacheable("authors")`.
* Demonstrate that repeated calls with the same `id` use the cache.

---

## **Task 2 — Cache eviction (single entry)**

* Add a method `deleteAuthor(int id)` in `AuthorService`.
* Use `@CacheEvict(cacheNames = "authors", key = "#id")` to remove a single cached entry.
* Verify (with logs or breakpoints) that after eviction, fetching the author hits the repository again.

---

## **Task 3 — Cache eviction (all entries)**

* Add a method `clearAuthorsCache()` in `AuthorService`.
* Use `@CacheEvict(cacheNames = "authors", allEntries = true)` to remove all cached entries.
* Demonstrate that after clearing, all authors are loaded from the repository again.

---

## **Task 4 — Composite key using SpEL**

* Add a new method `findAuthorByNameAndCountry(String name, String country)` in `AuthorService`.
* Use `@Cacheable(cacheNames = "authors", key = "#name + '-' + #country")`.
* Demonstrate that `"Smith-USA"` and `"Smith-UK"` produce different cache entries.

---

## **Task 5 — Custom KeyGenerator**

* Implement your own `KeyGenerator` (e.g. prefix keys with `"AUTHOR-" + id`).
* Register it as a Spring bean.
* Use it in `AuthorService` with `@Cacheable(keyGenerator = "customAuthorKeyGenerator")`.
* Verify that keys stored in the cache follow your custom format.

---

## **Task 6 — Write an integration test using spies**

* Use `@SpringBootTest` and `@MockitoSpyBean` on `AuthorRepository`.
* Write a test that calls `getAuthorById(1)` twice.
* Assert that `AuthorRepository.findById(1)` was invoked only once.
* Write another test that evicts the cache and proves the repository is called again.

## **Task 7 — Write an integration test using CacheManager**

Write an integration test that:
* Calls a cached method (getAuthorById).
* Accesses the "authors" cache directly via CacheManager.
* Verifies that the returned value is present in the cache.
* After eviction, verifies that the entry is no longer in the cache.


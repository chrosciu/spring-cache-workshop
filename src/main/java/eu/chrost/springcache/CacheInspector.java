package eu.chrost.springcache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheInspector {
    private final CacheManager cacheManager;

    public void logCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            log.info("Cache not found: {}", cacheName);
            return;
        }

        if (cache.getNativeCache() instanceof java.util.concurrent.ConcurrentMap<?,?> nativeCache) {
            log.info("Cache content for '{}':", cacheName);
            if (nativeCache.isEmpty()) {
                log.info("No items");
            } else {
                nativeCache.forEach((k, v) -> log.info("{} => {}", k, v));
            }
        } else {
            log.info("Cache '{}' is not a ConcurrentMap (type = {})", cacheName, cache.getNativeCache().getClass());
        }
    }
}

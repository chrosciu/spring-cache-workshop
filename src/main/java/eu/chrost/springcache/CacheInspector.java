package eu.chrost.springcache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Component
@RequiredArgsConstructor
@Slf4j
class CacheInspector {
    private final CacheManager cacheManager;

    public void logCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            log.info("Cache not found: {}", cacheName);
            return;
        }
        Object nativeCache = cache.getNativeCache();
        log.info("Cache '{}' is of type {}", cacheName, nativeCache.getClass());
        switch (nativeCache) {
            case ConcurrentMap<?,?> map -> logMap(map);
            case com.github.benmanes.caffeine.cache.Cache<?, ?> caffeineCache -> logMap(caffeineCache.asMap());
            default -> log.info("Cache is not a supported type");
        }
    }

    private void logMap(Map<?, ?> map) {
        if (map.isEmpty()) {
            log.info("No items");
        } else {
            map.forEach((k, v) -> log.info("{} => {}", k, v));
        }
    }
}

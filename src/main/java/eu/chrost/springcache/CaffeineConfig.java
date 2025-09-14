package eu.chrost.springcache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
class CaffeineConfig {
    @Bean
    public Caffeine caffeine() {
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .initialCapacity(10)
                .maximumSize(50);
    }
}

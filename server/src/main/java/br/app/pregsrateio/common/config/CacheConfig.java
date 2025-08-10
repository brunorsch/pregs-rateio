package br.app.pregsrateio.common.config;

import static java.util.List.of;
import static java.util.concurrent.TimeUnit.MINUTES;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        CaffeineCache cachePrincipals = new CaffeineCache(
            "principals",
            Caffeine.newBuilder()
                .expireAfterAccess(15, MINUTES)
                .maximumSize(1000)
                .build()
        );

        cacheManager.setCaches(of(cachePrincipals));
        return cacheManager;
    }
}
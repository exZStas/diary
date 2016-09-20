package com.vm62.diary.integration.core.common;

import com.google.inject.Singleton;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import static javax.cache.expiry.Duration.ONE_HOUR;

@Singleton
public class CacheManager {


    private final CachingProvider provider;
    private final javax.cache.CacheManager cacheManager;

    public CacheManager() {
        provider = Caching.getCachingProvider();
        cacheManager = provider.getCacheManager();

        // example creating cache
//        cacheManager.createCache(NAME_OF_CACHE,
//                new MutableConfiguration<String, List>()
//                    .setTypes(String.class, List.class)
//                    .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(ONE_HOUR)));


    }

}

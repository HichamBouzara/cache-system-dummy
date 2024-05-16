package com.cachesystem.cacheapi.service;

import org.springframework.stereotype.Service;

import com.cachesystem.cacheapi.model.Cache;

import javax.annotation.PreDestroy;

@Service
public class CacheService {

    private final Cache<String, String> cache;

    public CacheService() {
        this.cache = new Cache<>(30000); // TTL 30 sec
    }

    public void put(String key, String value) {
        cache.put(key, value);
    }

    public String get(String key) {
        return cache.get(key);
    }

    @PreDestroy
    public void shutdown() {
        cache.shutdown();
    }
}

package com.cachesystem.cacheapi.model;

import java.util.*;
import java.util.concurrent.*;

public class Cache<K, V> {
    private final Map<K, V> cache = new ConcurrentHashMap<>();
    private final Map<K, Long> timestamps = new ConcurrentHashMap<>();
    private final long ttl;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Cache(long ttl) {
        this.ttl = ttl;
        // Schedule the cleanup task to run periodically
        scheduler.scheduleAtFixedRate(this::removeExpiredEntries, ttl, ttl, TimeUnit.MILLISECONDS);
    }

    public void put(K key, V value) {
        cache.put(key, value);
        timestamps.put(key, System.currentTimeMillis());
    }

    public V get(K key) {
        Long storedTime = timestamps.get(key);
        if (storedTime == null || System.currentTimeMillis() - storedTime > ttl) {
            cache.remove(key);
            timestamps.remove(key);
            return null;
        }
        return cache.get(key);
    }

    private void removeExpiredEntries() {
        long currentTime = System.currentTimeMillis();
        for (K key : timestamps.keySet()) {
            Long storedTime = timestamps.get(key);
            if (storedTime != null && currentTime - storedTime > ttl) {
                cache.remove(key);
                timestamps.remove(key);
            }
        }
    }

    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

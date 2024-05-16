package com.cachesystem.cacheapi;

import com.cachesystem.cacheapi.service.CacheService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CacheServiceTest {

    @Test
    public void testCachePutAndGet() {
        CacheService cacheService = new CacheService();
        cacheService.put("key1", "value1");
        assertEquals("value1", cacheService.get("key1"));
    }

    @Test
    public void testCacheTTL() throws InterruptedException {
        CacheService cacheService = new CacheService();
        cacheService.put("key1", "value1");
        Thread.sleep(40000); // Wait for longer than the TTL (40 sec)
        assertNull(cacheService.get("key1"));
    }
}

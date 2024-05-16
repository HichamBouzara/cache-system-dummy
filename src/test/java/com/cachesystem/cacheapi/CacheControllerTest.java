package com.cachesystem.cacheapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

// Please be patient, the tests may take some time to run due to the TTL (30 sec) and sleep (40 sec) in the test cases.

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CacheControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testStoreAndGet() {
        restTemplate.postForObject("/cache/store?key=key1&value=value1", null, Void.class);
        ResponseEntity<String> response = restTemplate.getForEntity("/cache/get?key=key1", String.class);
        assertEquals("value1", response.getBody());
    }

    @Test
    public void testGetNonExistentKey() {
        ResponseEntity<String> response = restTemplate.getForEntity("/cache/get?key=nonexistent", String.class);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testTTL() throws InterruptedException {
        restTemplate.postForObject("/cache/store?key=key1&value=value1", null, Void.class);
        Thread.sleep(40000);
        ResponseEntity<String> response = restTemplate.getForEntity("/cache/get?key=key1", String.class);
        assertEquals(404, response.getStatusCode().value());
    }
}

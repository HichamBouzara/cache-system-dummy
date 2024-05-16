package com.cachesystem.cacheapi.controller;

import com.cachesystem.cacheapi.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private final CacheService cacheService;

    @Autowired
    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping("/store")
    public ResponseEntity<Void> store(@RequestParam String key, @RequestParam String value) {
        try {
            cacheService.put(key, value);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get")
    public ResponseEntity<String> get(@RequestParam String key) {
        String value = cacheService.get(key);
        if (value != null) {
            return ResponseEntity.ok(value);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Key not found or expired");
        }
    }
}

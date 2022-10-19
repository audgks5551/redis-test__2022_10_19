package com.example.redistest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("/test")
    public ResponseEntity add() {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set("test1", "1");
        vop.set("test2", "2");
        vop.set("test3", "3");
        vop.set("test4", "4");
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/test/{key}")
    public ResponseEntity get(@PathVariable String key) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.get(key);
        return ResponseEntity.ok(value);
    }

}

package com.example.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.util.Set;
@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public void Set(String key, String value) {this.redisTemplate.opsForValue().set(key,value);}
    public String Get(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }
    public Set getAllkeys() {return this.redisTemplate.keys("*");}
    public void Del(String key) {this.redisTemplate.delete(key);}
}

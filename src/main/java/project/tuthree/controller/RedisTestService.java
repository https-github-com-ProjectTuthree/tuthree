package project.tuthree.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisTestService {

    private final StringRedisTemplate redisTemplate;

    public String getRedisStringValue(String key) {
        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        log.info("Redis key : " + key);
        log.info("Redis value : " + stringValueOperations.get(key));
        return stringValueOperations.get(key);

    }

    public String setRedisStringValue(String key, String value) {
        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        stringValueOperations.set(key, value);
        log.info("Redis key : " + key);
        log.info("Redis value : " + stringValueOperations.get(key));
        return stringValueOperations.get(key);
    }
}

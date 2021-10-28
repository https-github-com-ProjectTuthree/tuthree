package project.tuthree.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisTestController {

    private final RedisTestService redisTestService;

    @PostMapping("/getRedis")
    public String RedisTest(@RequestParam("key") String key) {
        return redisTestService.getRedisStringValue(key);
    }

    @PostMapping("/setRedis")
    public String RedisSaveTest(@RequestParam("key") String key, @RequestParam("val") String val) {
        return redisTestService.setRedisStringValue(key, val);
    }
}

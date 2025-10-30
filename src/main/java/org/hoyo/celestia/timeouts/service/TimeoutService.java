package org.hoyo.celestia.timeouts.service;

import org.hoyo.celestia.timeouts.TimeoutRepository;
import org.hoyo.celestia.timeouts.model.Timeout;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TimeoutService {

    private static final long TIMEOUT_SECONDS = 60;
    private final RedisTemplate<String, String> redisTemplate;
    private final TimeoutRepository timeoutRepository;

    public TimeoutService(RedisTemplate<String, String> redisTemplate, TimeoutRepository timeoutRepository) {
        this.redisTemplate = redisTemplate;
        this.timeoutRepository = timeoutRepository;
    }

    /**
     * Returns true if timeout is over (can make Enka call),
     * false otherwise.
     */
    public Boolean canIEnkaCallYet(String uid) {
        String key = buildKey(uid);
        // If key does not exist, timeout expired
        Boolean hasKey = redisTemplate.hasKey(key);
        return (hasKey == null || !hasKey);
    }

    /**
     * Creates or updates a timeout for the given UID.
     */
    public void upsertTimeoutByUID(String uid) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String key = buildKey(uid);

        //mongo timeout insert
        timeoutRepository.save(new Timeout(uid, Instant.now()));

        // Store timestamp with TTL (auto-expiry after 60s)
        ops.set(key, Instant.now().toString(), TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * Returns how long before the timeout expires (in seconds).
     * Returns 0 if timeout does not exist.
     */
    public ResponseEntity<Long> timeLeft(String uid) {
        Optional<Timeout> optionalTimeout = timeoutRepository.findByUid(uid);
        if(optionalTimeout.isPresent()){
            Timeout timeout = optionalTimeout.get();
            Instant savedTime = timeout.getTimestamp();
            savedTime = savedTime.plusSeconds(60);
            Instant currentTime = Instant.now();
            return ResponseEntity.status(HttpStatus.OK).body(Duration.between(savedTime, currentTime).toSeconds());
        }
        return ResponseEntity.status(HttpStatus.OK).body(0L);
    }

    private String buildKey(String uid) {
        return "timeout:" + uid;
    }
}

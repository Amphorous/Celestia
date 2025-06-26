package org.hoyo.celestia.timeouts.service;

import org.hoyo.celestia.timeouts.TimeoutRepository;
import org.hoyo.celestia.timeouts.model.Timeout;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TimeoutService {
    private final TimeoutRepository timeoutRepository;
    public TimeoutService(TimeoutRepository timeoutRepository) {
        this.timeoutRepository = timeoutRepository;
    }

    public Boolean canIEnkaCallYet(String uid) {
        //get timeout for uid, return true if timeout is over, false otherwise
        Optional<Timeout> optionalTimeout = timeoutRepository.findByUid(uid);
        if(optionalTimeout.isPresent()){
            Timeout timeout = optionalTimeout.get();
            Instant savedTime = timeout.getTimestamp();
            savedTime = savedTime.plusSeconds(60);
            Instant currentTime = Instant.now();
            return (currentTime.isAfter(savedTime));
        }
        return true;
    }

    //creates a timeout in mongo for a UID
    public void upsertTimeoutByUID(String uid) {
        Optional<Timeout> optionalTimeout = timeoutRepository.findByUid(uid);

        if (optionalTimeout.isPresent()) {
            Timeout timeout = optionalTimeout.get();
            timeout.setTimestamp(Instant.now());
            timeoutRepository.save(timeout);
        } else {
            // Optionally: create a new document if none exists
            Timeout newTimeout = new Timeout();
            newTimeout.setUid(uid);
            newTimeout.setTimestamp(Instant.now());
            timeoutRepository.save(newTimeout);
        }
    }
}


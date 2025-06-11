package org.hoyo.celestia.timeouts;


import org.hoyo.celestia.timeouts.model.Timeout;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeoutRepository extends MongoRepository<Timeout, String> {
    Optional<Timeout> findByUid(String uid);
    Boolean existsByUid(String uid);

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Query(value = "{}", delete = true)
    long deleteAllDocuments();
    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

}


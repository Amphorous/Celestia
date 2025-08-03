package org.hoyo.celestia.user.repository;

import org.hoyo.celestia.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {
    Optional<User> findByUid(String uid);
    void deleteByUid(String uid);

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Query(value = "{}", delete = true)
    long deleteAllDocuments();
    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}

package org.hoyo.celestia.loaders;

import org.hoyo.celestia.loaders.model.Store;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends Neo4jRepository<Store, Long> {

    @Query("""
        MERGE (s:Store {type: $type})
        RETURN s
    """)
    Store createStoreIfNotExists(@Param("type") String type);

    Optional<Store> findByName(String weapons);
}

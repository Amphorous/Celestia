package org.hoyo.celestia.loaders;

import org.hoyo.celestia.loaders.model.StoreNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreNodeRepository extends Neo4jRepository<StoreNode, Long> {

    @Query("""
        MERGE (s:Store {type: $type})
        RETURN s
    """)
    StoreNode createStoreIfNotExists(@Param("type") String type);

    Optional<StoreNode> findByName(String weapons);
}

package org.hoyo.celestia.uids;

import org.hoyo.celestia.uids.model.UIDNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface UIDNodeRepository extends Neo4jRepository<UIDNode, Long> {
    @Query("""
        MERGE (u:UIDNode {uid: $uid})
        RETURN u
    """)
    UIDNode createUIDNodeIfNotExists(@Param("uid") String uid);
}

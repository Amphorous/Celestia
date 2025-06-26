package org.hoyo.celestia.relics;

import org.hoyo.celestia.relics.model.RelicNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RelicNodeRepository extends Neo4jRepository<RelicNode, Long> {

    @Query("""
            MERGE (r:RelicNode {
                mainAffixId: $relicNode.mainAffixId
            })
            return r
            """)
    public RelicNode linkRelicNodeToStore(String relicId, String uid, RelicNode relicNode);
}

package org.hoyo.celestia.relics;

import org.hoyo.celestia.relics.model.RelicNode;
import org.hoyo.celestia.relics.model.SubAffixNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface RelicNodeRepository extends Neo4jRepository<RelicNode, Long> {

    @Query("""
    CREATE (r:RelicNode {
        relicId: $relicId,
        mainAffixId: $mainAffixId,
        tid: $tid,
        type: $type,
        level: $level,
        setId: $setId,
        setName: $setName,
        mainType: $mainType,
        mainValue: $mainValue
    })
            
    WITH r
    MATCH (u:UIDNode {uid: $uid})
    CREATE (u)-[:RELIC]->(r)
            
    WITH r, $subAffixes AS subAffixes
    UNWIND subAffixes AS sa
    CREATE (s:SubAffixNode {
        type: sa.type,
        value: sa.value,
        cnt: sa.cnt,
        step: sa.step
    })
    CREATE (r)-[:SUBAFFIX]->(s)
            
    RETURN DISTINCT r
            
    """)
    RelicNode insertRelic(
            String relicId,
            String uid,
            String mainAffixId,
            String tid,
            String type,
            String level,
            String setId,
            String setName,
            String mainType,
            Double mainValue,
            List<Map<String, Object>> subAffixes
    );

    @Query("""
            RETURN EXISTS( (:UIDNode {uid: $uid})-[:RELIC]->(:RelicNode {relicId: $relicId}) )
            """)
    Boolean existsRelic(String relicId, String uid);

    @Query("""
            MATCH (u:UIDNode {UID: $uid})-[:OWNS_BUILD]->(b:BuildNode {avatarId: $avatarId, isStatic: $isStatic})
            MATCH (b)-[:EQUIPS_RELIC]->(r:RelicNode)
            RETURN COLLECT(DISTINCT r.RelicId) AS relicIds
            """)
    Set<String> getAllRelicIdsFromStaticNode(String uid, String avatarId, Boolean isStatic);
}

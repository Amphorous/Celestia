package org.hoyo.celestia.builds;

import org.hoyo.celestia.builds.model.BuildNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;
import java.util.Set;

public interface BuildNodeRepository extends Neo4jRepository<BuildNode, Long> {

    @Query("""
        RETURN EXISTS(
            MATCH (u:UIDNode {uid: $uid})-[:HAS_BUILD]->(:BuildNode {avatarId: $avatarId})
        )
    """)
    Boolean hasBuilds(@Param("uid") String uid, @Param("avatarId") String avatarId);

    @Query("""
            RETURN EXISTS{
                MATCH (u:UIDNode {uid: $uid})-[:HAS_BUILD]->(b:BuildNode {avatarId: $avatarId, level: $level, skillListString: $skillListString, isStatic: true})
            }
            """)
    Boolean hasLevelsOnStaticBuild(@Param("uid") String uid, @Param("avatarId") String avatarId, @Param("skillListString") String skillListString, @Param("level") Integer level);

    @Query("""
    MATCH (u:UIDNode {uid: $uid})

    OPTIONAL MATCH (u)-[:HAS_BUILD]->(old:BuildNode {avatarId: $avatarId, isStatic: $isStatic})-[:FIGHT_PROPS]->(f:FightPropNode)
    DETACH DELETE f
    DETACH DELETE old

    CREATE (b1:BuildNode {
        level: $level,
        skillListString: $skillListString,
        isStatic: $isStatic,
        avatarId: $avatarId,
        buildName: $buildName,
        isHidden: $isHidden
    })
    CREATE (u)-[:HAS_BUILD]->(b1)

    WITH u, b1, $fightPropMap AS fightPropMap, $relicIds AS relicIds

    CALL apoc.create.node(['FightPropNode'], fightPropMap) YIELD node AS f1
    CREATE (b1)-[:FIGHT_PROPS]->(f1)

    WITH u, b1, relicIds
    UNWIND relicIds AS rid
    MATCH (u)-[:OWNS_RELIC]->(r:RelicNode {relicId: rid})
    CREATE (b1)-[:EQUIPS_RELIC]->(r)

    RETURN DISTINCT b1
    """)
    BuildNode removeIsStaticBuildAndItsFightPropNodeThenInsertANewIsStaticBuildAndItsFightPropNodeAndAlsoLinkTheBuildNodeToItsRelicNodes(
            @Param("uid") String uid,
            @Param("avatarId") String avatarId,
            @Param("level") Integer level,
            @Param("skillListString") String skillListString,
            @Param("isStatic") Boolean isStatic,
            @Param("isHidden") Boolean isHidden,
            @Param("buildName") String buildName,
            @Param("fightPropMap") Map<String, Object> fightPropMap,
            @Param("relicIds") Set<String> relicIds
    );

}

package org.hoyo.celestia.builds;

import org.hoyo.celestia.builds.model.BuildNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface BuildNodeRepository extends Neo4jRepository<BuildNode, Long> {

    @Query("""
        RETURN EXISTS(
            MATCH (u:UIDNode {uid: $uid})-[:HAS_BUILDS {avatarId: $avatarId}]->(:BuildNode)
        )
    """)
    Boolean hasBuilds(@Param("uid") String uid, @Param("avatarId") String avatarId);

    @Query("""
            RETURN EXISTS(
                MATCH (u:UIDNode {uid: $uid})-[:HAS_BUILDS {avatarId: $avatarId}]->(b:BuildNode {level: $level, skillListString: $skillListString, isStatic: true})
            )
            """)
    Boolean hasLevelsOnStaticBuild(@Param("uid") String uid, @Param("avatarId") String avatarId, @Param("skillListString") String skillListString, @Param("level") Integer level);
}

package org.hoyo.celestia.loaders;

import org.hoyo.celestia.loaders.model.WeaponNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponNodeRepository extends Neo4jRepository<WeaponNode, String> {

    @Query("""
    MERGE (s:Store {name: 'weapons'})
    WITH s
    MATCH (w:WeaponNode {weaponId: $weaponId})
    
    OPTIONAL MATCH (s)-[oldRel:CONTAINS_WEAPON]->(w)
    DELETE oldRel

    MERGE (s)-[newRel:CONTAINS_WEAPON]->(w)
    SET newRel.weaponId = $weaponId,
        newRel.rarity = $rarity,
        newRel.path = $path

    RETURN w
""")
    WeaponNode linkWeaponNodeToStore(
            @Param("weaponId") String weaponId,
            @Param("path") String path,
            @Param("rarity") String rarity
    );

}

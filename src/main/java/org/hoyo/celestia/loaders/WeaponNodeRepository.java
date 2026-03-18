package org.hoyo.celestia.loaders;

import org.hoyo.celestia.loaders.model.WeaponNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponNodeRepository extends Neo4jRepository<WeaponNode, String> {

    /**
     * MAKE SURE OF THE FOLLOWING:
     *
     * CREATE CONSTRAINT store_name IF NOT EXISTS
     * FOR (s:Store) REQUIRE s.name IS UNIQUE;
     *
     * */

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

    @Query("""
    MERGE (s:Store {name: 'weapons'})
    CREATE (w:WeaponNode {
            weaponId: $weaponId,
            nameHash: $nameHash,
            imagePath: $imagePath,
            baseAttack: $baseAttack,
            baseDefense: $baseDefense,
            baseHP: $baseHP
        }
    )
    WITH s, w

    MERGE (s)-[newRel:CONTAINS_WEAPON]->(w)
    SET newRel.weaponId = $weaponId,
        newRel.rarity = $rarity,
        newRel.path = $path

    RETURN w
""")
    WeaponNode createAndLinkWeaponNodeToStore(
            @Param("weaponId") String weaponId,
            @Param("path") String path,
            @Param("rarity") String rarity,
            @Param("nameHash") String nameHash,
            @Param("imagePath") String imagePath,
            @Param("baseAttack") double baseAttack,
            @Param("baseDefense") double baseDefense,
            @Param("baseHP") double baseHP
    );

    //check if (s) exists, then check if (s)-[r:CONTAINS_WEAPON.weaponId == $weaponId]->(w)
    @Query("""
            MERGE (s:Store {name: 'weapons'})
            RETURN EXISTS {
                MATCH (s)-[:CONTAINS_WEAPON {weaponId: $weaponId}]->(w)
            }
            """)
    Boolean checkIfWeaponExists(
            @Param("weaponId") String weaponId
    );

}

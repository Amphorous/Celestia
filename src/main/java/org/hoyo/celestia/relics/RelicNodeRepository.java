package org.hoyo.celestia.relics;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RelicNodeRepository extends Neo4jRepository<RelicNode, Long> {
}

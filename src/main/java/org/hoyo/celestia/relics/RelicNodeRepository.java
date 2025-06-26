package org.hoyo.celestia.relics;

import org.hoyo.celestia.relics.model.RelicNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelicNodeRepository extends Neo4jRepository<RelicNode, Long> {

}

package org.hoyo.celestia.builds;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface BuildRepository extends Neo4jRepository<Build,String> {
}

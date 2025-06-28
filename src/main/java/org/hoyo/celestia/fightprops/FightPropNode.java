package org.hoyo.celestia.fightprops;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Map;

@Data
@Node
public class FightPropNode {

    @Id
    @GeneratedValue
    private Long id;
    private Map<String, Double> stats;
}

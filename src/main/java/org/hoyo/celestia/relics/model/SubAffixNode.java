package org.hoyo.celestia.relics.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node
public class SubAffixNode {
    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private Double value;
    private Integer cnt;
    private Integer step;
}

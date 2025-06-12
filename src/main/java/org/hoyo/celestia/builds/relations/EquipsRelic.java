package org.hoyo.celestia.builds.relations;

import lombok.Data;
import org.hoyo.celestia.relics.model.RelicNode;
import org.springframework.data.neo4j.core.schema.*;

@Data
@RelationshipProperties
public class EquipsRelic {
    @Id @GeneratedValue
    private String id;

    @Property("slot")
    private String type;

    @TargetNode
    private RelicNode relicNode;
}

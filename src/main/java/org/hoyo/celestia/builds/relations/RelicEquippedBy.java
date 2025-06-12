package org.hoyo.celestia.builds.relations;

import lombok.Data;
import org.hoyo.celestia.builds.model.Build;
import org.springframework.data.neo4j.core.schema.*;

@Data
@RelationshipProperties
public class RelicEquippedBy {
    @Id
    @GeneratedValue
    private String id;

    @Property("slot")
    private String type;

    @TargetNode
    private Build build;
}

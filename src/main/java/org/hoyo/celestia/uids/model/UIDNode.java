package org.hoyo.celestia.uids.model;

import lombok.Data;
import org.hoyo.celestia.relics.model.RelicNode;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Data
@Node
public class UIDNode {
    @Id
    @GeneratedValue
    private Long id;
    private String uid;
    private Boolean isActivated = true;

    //relations to builds and relics

    //relation to BuildNode called HAS_BUILD
    // (prop - String avatarId) no props

    //relation to RelicNode called OWNS_RELIC
    // no props
    @Relationship(type = "OWNS_RELIC", direction = Relationship.Direction.OUTGOING)
    private List<RelicNode> relics;
}

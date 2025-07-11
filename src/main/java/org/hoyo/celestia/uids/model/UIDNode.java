package org.hoyo.celestia.uids.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

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
}

package org.hoyo.celestia.builds.model;

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

    //relations to builds and relics
}

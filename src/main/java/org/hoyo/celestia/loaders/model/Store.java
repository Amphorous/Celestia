package org.hoyo.celestia.loaders.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node
public class Store {
    @Id
    @GeneratedValue
    private String id;
    private String name;
}

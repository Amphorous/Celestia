package org.hoyo.celestia.loaders.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node
public class WeaponNode {
    @Id
    @GeneratedValue
    private String id;
    private String nameHash;
    private String imagePath;
    private WeaponMaxStat lvl80Stats;
}

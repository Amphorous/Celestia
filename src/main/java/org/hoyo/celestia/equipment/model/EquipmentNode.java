package org.hoyo.celestia.equipment.model;

import lombok.Data;
import org.hoyo.celestia.user.model.Flat;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data @Node
public class EquipmentNode {
    @Id @GeneratedValue
    private String id;
    private Integer rank;
    private String tid;
    private Integer promotion;
    private Integer level;
    private Flat _flat;
    private String wepHash;
}

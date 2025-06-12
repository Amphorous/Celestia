package org.hoyo.celestia.builds.relations;

import lombok.Data;
import org.hoyo.celestia.equipment.model.EquipmentNode;
import org.hoyo.celestia.user.model.Flat;
import org.springframework.data.neo4j.core.schema.*;

@Data
@RelationshipProperties
public class EquipsWeapon {
    @Id
    @GeneratedValue
    private String id;

    @Property("rank")
    private Integer rank;
    @Property("promotion")
    private Integer promotion;
    @Property("level")
    private Integer level;
    @Property("flat")
    private Flat _flat;

    @TargetNode
    private EquipmentNode equipmentNode;
}

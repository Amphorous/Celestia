package org.hoyo.celestia.relics.model;

import lombok.Data;
import org.hoyo.celestia.builds.relations.RelicEquippedBy;
import org.hoyo.celestia.user.model.Flat;
import org.hoyo.celestia.user.model.SubAffix;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data @Node
public class RelicNode {
    @Id @GeneratedValue
    private String id;
    private Integer mainAffixId;
    private ArrayList<SubAffix> subAffixList;
    private String tid;
    private Integer type;
    private Integer level;
    private Flat _flat;

    @Relationship(type="EQUIPS_RELIC", direction = Relationship.Direction.INCOMING)
    private List<RelicEquippedBy> relicEquippedBy;
}


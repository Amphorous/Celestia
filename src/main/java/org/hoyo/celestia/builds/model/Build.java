package org.hoyo.celestia.builds.model;


import lombok.Data;
import org.hoyo.celestia.builds.relations.EquipsRelic;
import org.hoyo.celestia.builds.relations.EquipsWeapon;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Data @Node
public class Build {
    @Id @GeneratedValue
    private String id;
    private String buildName="frozenunicorn123";
    private String avatarId;

    @Relationship(type="EQUIPS_RELIC", direction = Relationship.Direction.OUTGOING)
    private List<EquipsRelic> equipsRelic;

    @Relationship(type="EQUIPS_WEAPON", direction = Relationship.Direction.OUTGOING)
    private EquipsWeapon equipsWeapon;
}

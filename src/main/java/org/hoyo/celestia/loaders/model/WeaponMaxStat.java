package org.hoyo.celestia.loaders.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.CompositeProperty;

@Data
public class WeaponMaxStat {
    private double baseAttack;
    private double baseDefense;
    private double baseHP;
}

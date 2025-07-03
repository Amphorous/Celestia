package org.hoyo.celestia.buffEffects.model;

import lombok.Data;

import java.util.Map;

@Data
public class TeamMemberDTO {

    private Map<String, Double> stats;
    private Map<Integer, Integer> relicSets;
    private String weaponId;
    private Integer weaponRank;
}

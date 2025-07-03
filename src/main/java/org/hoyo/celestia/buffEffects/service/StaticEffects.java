package org.hoyo.celestia.buffEffects.service;

import org.hoyo.celestia.buffEffects.model.TeamMemberDTO;
import org.hoyo.celestia.loaders.global.GlobalMetaFileLoader;
import org.hoyo.celestia.loaders.model.metaModel.SetSkillData;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StaticEffects {
    private final GlobalMetaFileLoader globalMetaFileLoader;

    public StaticEffects(GlobalMetaFileLoader globalMetaFileLoader) {
        this.globalMetaFileLoader = globalMetaFileLoader;
    }

    public void effectSwitch(TeamMemberDTO teamMember) {
        Map<String,Double> stats = teamMember.getStats();
        Map<Integer,Integer> relicSets = teamMember.getRelicSets();
        String weaponId = teamMember.getWeaponId();
        Integer weaponRank = teamMember.getWeaponRank();

        //accesses metafile to fetch the relevant static buffs
        for(Map.Entry<Integer,Integer> entry : relicSets.entrySet()) {
            Integer relicSet = entry.getKey();
            Integer relicCount = entry.getValue();
            relicEffectRouting(stats, relicSet, relicCount);
            weaponEffectRouting(stats, weaponId, weaponRank);
        }

    }

    private void weaponEffectRouting(Map<String, Double> stats, String weaponId, Integer rank) {
        Map<String, Double> context = null;
        try{
            context = globalMetaFileLoader.getMetaFile().getEquipmentSkill().get(weaponId).get(String.valueOf(rank)).get("props");
        } catch (NullPointerException e) {
            //weapon prolly doesnt have a static effect
            return;
        }
        if(context == null) {return;}

        for(Map.Entry<String, Double> entry : context.entrySet()) {
            effectRoutingStatAdder(stats, entry);
        }
    }

    public void relicEffectRouting(Map<String,Double> stats, Integer relicSet, Integer relicCount) {
        Map<String, SetSkillData> context = globalMetaFileLoader.getMetaFile().getRelic().getSetSkill().get(String.valueOf(relicSet));
        if(context == null) {return;}

        if(relicCount == 4) {
            for(Map.Entry<String,Double> entry : context.get("4").getProps().entrySet()) {
                effectRoutingStatAdder(stats, entry);
            }
        }
        if(relicCount >= 2) {
            for(Map.Entry<String,Double> entry : context.get("2").getProps().entrySet()) {
                effectRoutingStatAdder(stats, entry);
            }
        }
    }

    public static void effectRoutingStatAdder(Map<String, Double> stats, Map.Entry<String, Double> entry) {
        String key = entry.getKey();
        Double value = entry.getValue();
        if(key.substring(key.length()-4).equalsIgnoreCase("base")){
            key = key.substring(0,key.length()-4);
        }
        stats.put(key, stats.getOrDefault(key, 0.0) + value);
    }

    public void effectAdd(Map<String,Double> stats, String targetStat, Double value) {
        stats.put(targetStat, value + stats.getOrDefault(targetStat, 0.0));
    }
}

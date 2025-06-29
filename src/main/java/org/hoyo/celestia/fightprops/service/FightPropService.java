package org.hoyo.celestia.fightprops.service;

import org.hoyo.celestia.fightprops.model.FightPropNode;
import org.hoyo.celestia.loaders.global.GlobalMetaFileLoader;
import org.hoyo.celestia.loaders.model.HonkerMetaObject;
import org.hoyo.celestia.user.model.AvatarDetail;
import org.hoyo.celestia.user.model.Props;
import org.hoyo.celestia.user.model.Relic;
import org.hoyo.celestia.user.model.Skill;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FightPropService {

    private final GlobalMetaFileLoader globalMetaFileLoader;

    public FightPropService(GlobalMetaFileLoader globalMetaFileLoader) {
        this.globalMetaFileLoader = globalMetaFileLoader;
    }

    public FightPropNode getFightPropMap(AvatarDetail character){
        FightPropNode fightPropNode = new FightPropNode();

        //get character base stats from metaFile
        //get weapon base from metaFile
        HonkerMetaObject localMetaFile = globalMetaFileLoader.getMetaFile();
        //localMetaFile.getAvatar().get(character.getAvatarId()).get(String.valueOf(character.getPromotion()))
        String avatarId = character.getAvatarId();
        String promotion = String.valueOf(character.getPromotion());
        Integer level = character.getLevel();

        Map<String, Double> fightPropMap = new HashMap<>();
        Map<String, Double> currentAscensionStats = localMetaFile.getAvatar().get(avatarId).get(promotion);
        Double addValue = null;
        for(Map.Entry<String, Double> entry : currentAscensionStats.entrySet()) {
            String key = entry.getKey();
            boolean check = (key.substring(key.length()-3)).equalsIgnoreCase("add");
            if(check) {
                addValue = entry.getValue();
                continue;
            }
            if (addValue != null) {
                Double finalStatValue = (level - 1)*addValue + entry.getValue();
                String finalKey = "Base" + key.substring(0, key.length()-4);
                fightPropMap.put(finalKey, finalStatValue);
                addValue = null;
                continue;
            }
            fightPropMap.put(key, entry.getValue());
        }
        //wep stats calc
        for(Props prop : character.getEquipment().get_flat().getProps()){
            fightPropMap.put(prop.getType(),prop.getValue()+fightPropMap.getOrDefault(prop.getType(),0.0));
        }

        //iterate relics to get all stats from there
        for(Relic relic:character.getRelicList()){
            for(Props prop : relic.get_flat().getProps()){
                String key = prop.getType();
                if(key.substring(key.length()-4).equalsIgnoreCase("base")){
                    key = key.substring(0,key.length()-4);
                }
                fightPropMap.put(key,prop.getValue()+fightPropMap.getOrDefault(key,0.0));
            }
        }




        //finally get trace stats using AAAA2XX as skillId from metaFile.tree
        for(Skill skill : character.getSkillTreeList()){
            String pointId = skill.getPointId().toString();
            if(pointId.charAt(pointId.length()-3)=='2'){
                if(skill.getLevel()=='1'){
                    //now we need to add the stat value to fightpropmap
                    Map<String, Double> stat = localMetaFile.getTree().get(pointId).get("1").get("props");
                    for(Map.Entry<String, Double> entry : stat.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(key.substring(key.length()-4).equalsIgnoreCase("base")){
                            key = key.substring(0,key.length()-4);
                        }
                        fightPropMap.put(key, fightPropMap.getOrDefault(key,0.0)+value);
                    }
                }
            }
        }
        //metafile.getTree().get("skillid").get("1").get("props") <- this is a "map" of props, just iterate and add to set/add value to the record in fightprop
        fightPropNode.setStats(fightPropMap);

        System.out.println("Av ID: "+character.getAvatarId()+":::::::::::::::::::::::::::");
        for(Map.Entry<String, Double> entry : fightPropMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("END OF AV::::::::::::::::::");

        return fightPropNode;
    }
}

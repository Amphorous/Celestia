package org.hoyo.celestia.fightprops.service;

import org.hoyo.celestia.fightprops.model.FightPropNode;
import org.hoyo.celestia.loaders.global.GlobalMetaFileLoader;
import org.hoyo.celestia.loaders.model.HonkerMetaObject;
import org.hoyo.celestia.user.model.AvatarDetail;
import org.hoyo.celestia.user.model.Props;
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

        for(Props prop : character.getEquipment().get_flat().getProps()){
            fightPropMap.
        }

        //iterate relics to get all stats from there

        //finally get trace stats using AAAA2XX as skillId from metaFile.tree
        //metafile.getTree().get("skillid").get("1").get("props") <- this is a "map" of props, just iterate and add to set/add value to the record in fightprop

        return fightPropNode;
    }
}

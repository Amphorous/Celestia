package org.hoyo.celestia.fightprops.service;

import org.hoyo.celestia.fightprops.model.FightPropNode;
import org.hoyo.celestia.user.model.AvatarDetail;
import org.springframework.stereotype.Service;

@Service
public class FightPropService {

    public FightPropNode getFightPropMap(AvatarDetail character){
        FightPropNode fightPropNode = new FightPropNode();

        //get character base stats from metaFile
        //get weapon base from metaFile

        //iterate relics to get all stats from there

        //finally get trace stats using AAAA2XX as skillId from metaFile.tree
        //metafile.get("skillid").get("1").get("props") <- this is a "map" of props, just iterate and add to set/add value to the record in fightprop

        return fightPropNode;
    }
}

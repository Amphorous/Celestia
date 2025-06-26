package org.hoyo.celestia.relics.service;

import org.hoyo.celestia.relics.model.RelicNode;
import org.hoyo.celestia.user.model.Relic;
import org.hoyo.celestia.user.model.SubAffix;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CreateRelicService {

    public String calculateRelicId(Relic relic){
        try {
            String relicId="";
            relicId+=relic.getTid();
            relicId+=relic.getMainAffixId()+"_";
            ArrayList<SubAffix> subAffixList = relic.getSubAffixList();
            for(SubAffix subAffix : subAffixList){
                relicId+=subAffix.getAffixId()+"-"+subAffix.getCnt()+"-"+subAffix.getStep()+"_";
            }
            return relicId;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Unable to calculate relic ID");
            return "INVALID";
        }
    }

    public RelicNode createRelic(Relic relic){

        return null;
    }
}

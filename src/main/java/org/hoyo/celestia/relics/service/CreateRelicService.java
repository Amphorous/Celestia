package org.hoyo.celestia.relics.service;

import org.hoyo.celestia.relics.RelicNodeRepository;
import org.hoyo.celestia.relics.SubAffixNodeRepository;
import org.hoyo.celestia.relics.model.RelicNode;
import org.hoyo.celestia.relics.model.SubAffixNode;
import org.hoyo.celestia.user.model.Relic;
import org.hoyo.celestia.user.model.SubAffix;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateRelicService {

    private final RelicNodeRepository relicNodeRepository;
    private final SubAffixNodeRepository subAffixNodeRepository;

    public CreateRelicService(RelicNodeRepository relicNodeRepository, SubAffixNodeRepository subAffixNodeRepository) {
        this.relicNodeRepository = relicNodeRepository;
        this.subAffixNodeRepository = subAffixNodeRepository;
    }

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

    public String createRelicNode(Relic relic){
        RelicNode relicNode = new RelicNode();
        relicNode.setRelicId(calculateRelicId(relic));
        relicNode.setMainAffixId(String.valueOf(relic.getMainAffixId()));
        relicNode.setTid(relic.getTid());
        relicNode.setType(String.valueOf(relic.getType()));
        relicNode.setLevel(String.valueOf(relic.getLevel()));
        relicNode.setSetId(String.valueOf(relic.get_flat().getSetID()));
        relicNode.setSetName(relic.get_flat().getSetName());
        relicNode.setMainType(relic.get_flat().getProps().getFirst().getType());
        relicNode.setMainValue(relic.get_flat().getProps().getFirst().getValue());

        List<SubAffixNode> subAffixNodes = new ArrayList<>();
        int count = 1;
        for(SubAffix subAffix : relic.getSubAffixList()){
            SubAffixNode subAffixNode = new SubAffixNode();
            subAffixNode.setStep(subAffix.getStep());
            subAffixNode.setCnt(subAffix.getCnt());
            subAffixNode.setType(relic.get_flat().getProps().get(count).getType());
            subAffixNode.setValue(relic.get_flat().getProps().get(count).getValue());
            subAffixNodes.add(subAffixNode);
            count++;
        }

        relicNodeRepository.save(relicNode);
        //link it to relicStore under uid

        for(SubAffixNode subAffixNode : subAffixNodes){
            subAffixNodeRepository.save(subAffixNode);
            //link it to relicNode from above
        }


        return true;
    }
}

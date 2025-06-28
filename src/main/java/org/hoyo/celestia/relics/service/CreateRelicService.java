package org.hoyo.celestia.relics.service;

import org.hoyo.celestia.relics.RelicNodeRepository;
import org.hoyo.celestia.relics.SubAffixNodeRepository;
import org.hoyo.celestia.relics.model.RelicNode;
import org.hoyo.celestia.relics.model.SubAffixNode;
import org.hoyo.celestia.user.model.AvatarDetail;
import org.hoyo.celestia.user.model.Relic;
import org.hoyo.celestia.user.model.SubAffix;
import org.springframework.stereotype.Service;

import java.util.*;

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
            String relicId = "";
            relicId += relic.getType() + ":";
            relicId += relic.getTid() + "_";
            relicId += relic.getMainAffixId()+"_";
            ArrayList<SubAffix> subAffixList = relic.getSubAffixList();
            for(SubAffix subAffix : subAffixList){
                relicId += subAffix.getAffixId() + "-" + subAffix.getCnt() + "-" + subAffix.getStep() + "_";
            }
            return relicId;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Unable to calculate relic ID");
            return "INVALID";
        }
    }

    public Set<String> getRelicIdSetFromAvatarDetails(AvatarDetail character){
        Set<String> relicIdSet = new HashSet<>();
        for(Relic relic : character.getRelicList()){
            relicIdSet.add(calculateRelicId(relic));
        }
        return relicIdSet;
    }

    public String createRelicNode(Relic relic, String uid, String relicId){

        RelicNode relicNode = getRelicNode(relic);
        List<SubAffixNode> subAffixNodes = getSubAffixNodes(relic);

        relicNodeRepository.insertRelic(
                relicNode.getRelicId(),
                uid,
                relicNode.getMainAffixId(),
                relicNode.getTid(),
                relicNode.getType(),
                relicNode.getLevel(),
                relicNode.getSetId(),
                relicNode.getSetName(),
                relicNode.getMainType(),
                relicNode.getMainValue(),
                convertSubAffixes(subAffixNodes)
        );



        return relicNode.getRelicId();
    }

    private static List<SubAffixNode> getSubAffixNodes(Relic relic) {
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
        return subAffixNodes;
    }

    private static RelicNode getRelicNode(Relic relic) {
        RelicNode relicNode = new RelicNode();
        relicNode.setMainAffixId(String.valueOf(relic.getMainAffixId()));
        relicNode.setTid(relic.getTid());
        relicNode.setType(String.valueOf(relic.getType()));
        relicNode.setLevel(String.valueOf(relic.getLevel()));
        relicNode.setSetId(String.valueOf(relic.get_flat().getSetID()));
        relicNode.setSetName(relic.get_flat().getSetName());
        relicNode.setMainType(relic.get_flat().getProps().getFirst().getType());
        relicNode.setMainValue(relic.get_flat().getProps().getFirst().getValue());
        return relicNode;
    }

    public List<Map<String, Object>> convertSubAffixes(List<SubAffixNode> subAffixes) {
        return subAffixes.stream().map(s -> {
            Map<String, Object> map = new HashMap<>();
            map.put("type", s.getType());
            map.put("value", s.getValue());
            map.put("cnt", s.getCnt());
            map.put("step", s.getStep());
            return map;
        }).toList();
    }


}

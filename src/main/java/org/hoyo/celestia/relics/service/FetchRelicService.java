package org.hoyo.celestia.relics.service;

import org.hoyo.celestia.relics.DTOs.RelicProjectionDTO;
import org.hoyo.celestia.relics.RelicNodeRepository;
import org.hoyo.celestia.relics.model.RelicNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchRelicService {

    private static final int PAGE_LIMIT = 20;

    private final RelicNodeRepository relicNodeRepository;

    public FetchRelicService(RelicNodeRepository relicNodeRepository) {
        this.relicNodeRepository = relicNodeRepository;
    }

    //
    public ResponseEntity<List<RelicNode>> getUserRelics(String uid){
        List<RelicNode> relics = relicNodeRepository.findRelicsPaged(uid, 0, 2);
        System.out.println("::::::::::::::::::::::::::::::::::::");
        System.out.println(relics.get(0).getSubAffixes().size());
        System.out.println("::::::::::::::::::::::::::::::::::::");
        return ResponseEntity.ok(relics);
    }

    public ResponseEntity<List<RelicProjectionDTO>> getUserRelicsForDisplay(String uid, int pageNumber) {
        long skip = (long) (pageNumber - 1) * PAGE_LIMIT;
        List<RelicNode> relics = relicNodeRepository.findRelicsPaged(uid, skip, PAGE_LIMIT);

        List<RelicProjectionDTO> relicDTOs = new ArrayList<>();
        for (RelicNode relic : relics) {
            RelicProjectionDTO relicDTO = new RelicProjectionDTO();
            relicDTO.setRelic(relic);
            relicDTO.setBuilds(relicNodeRepository.findBuildsForRelic(relic.getRelicId()));
            relicDTOs.add(relicDTO);
        }

        return ResponseEntity.ok(relicDTOs);
    }
}

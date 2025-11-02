package org.hoyo.celestia.relics.DTOs;

import lombok.Data;
import org.hoyo.celestia.relics.model.RelicNode;

import java.util.List;

@Data
public class RelicProjectionDTO {
    private RelicNode relic;
    private List<BuildProjection> builds;
}

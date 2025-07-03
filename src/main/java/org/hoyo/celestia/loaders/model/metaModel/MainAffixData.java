package org.hoyo.celestia.loaders.model.metaModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainAffixData {
    private double BaseValue;
    private double LevelAdd;
    private String Property;
}

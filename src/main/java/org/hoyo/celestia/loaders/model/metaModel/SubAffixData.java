package org.hoyo.celestia.loaders.model.metaModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubAffixData {
    private double BaseValue;
    private String Property;
    private double StepValue;
}

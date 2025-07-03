package org.hoyo.celestia.loaders.model.metaModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetSkillData {
    private Map<String, Double> props;
}

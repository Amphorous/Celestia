package org.hoyo.celestia.loaders.model.metaModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelicMetaProperty {

    private Map<String, Map<String, MainAffixData>> mainAffix;
    private Map<String, Map<String, SetSkillData>> setSkill;
    private Map<String, Map<String, SubAffixData>> subAffix;
}

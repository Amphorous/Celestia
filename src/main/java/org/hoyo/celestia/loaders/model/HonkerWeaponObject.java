package org.hoyo.celestia.loaders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hoyo.celestia.loaders.model.EquipmentName;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HonkerWeaponObject {
    private String avatarBaseType;
    private EquipmentName equipmentName;
    private String imagePath;
    private Integer rarity;
}

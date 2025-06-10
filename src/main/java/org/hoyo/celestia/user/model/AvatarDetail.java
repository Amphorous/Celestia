package org.hoyo.celestia.user.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AvatarDetail {
    private Integer pos;
    private ArrayList<Relic> relicList;
    private Integer level;
    private Integer promotion;
    private ArrayList<Skill> skillTreeList;
    private Equipment equipment;
    private String avatarId;
    private Boolean _assist;
}

package org.hoyo.celestia.user.model;


import lombok.Data;

import java.util.ArrayList;

@Data
public class Relic {
    private Integer mainAffixId;
    private ArrayList<SubAffix> subAffixList;
    private String tid;
    private Integer type;
    private Integer level;
    private Flat _flat;
}

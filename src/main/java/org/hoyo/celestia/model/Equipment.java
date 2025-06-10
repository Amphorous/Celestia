package org.hoyo.celestia.model;

import lombok.Data;

@Data
public class Equipment {
    private Integer rank;
    private String tid;
    private Integer promotion;
    private Integer level;
    private Flat _flat;
}

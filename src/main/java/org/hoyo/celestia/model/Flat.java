package org.hoyo.celestia.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Flat {
    private ArrayList<Props> props;
    private String name;
    private String setName;
    private Integer setID; //I KNOW SETID AND SETNAME ARE FOR RELIC WHILE NAME IS FOR EQUIPMENT, I'M JUST COMBINING THE OBJ LEAVE IT BE
}

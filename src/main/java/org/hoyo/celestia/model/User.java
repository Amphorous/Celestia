package org.hoyo.celestia.model;

import lombok.Data;

@Data
public class User {
    private DetailInfo detailInfo;
    private Integer ttl;
    private String uid;

}

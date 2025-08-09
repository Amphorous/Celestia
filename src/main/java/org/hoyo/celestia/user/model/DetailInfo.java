package org.hoyo.celestia.user.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DetailInfo {
    private Integer worldLevel;
    private PrivacySettingInfo privacySettingInfo;
    private String platformAccountId;
    private String platformNickname;
    private Integer headIcon; //noref
    private String signature; //noref
    private ArrayList<AvatarDetail> avatarDetailList;
    private String platform;
    private RecordInfo recordInfo; //go in for noref
    private String uid;
    private Integer level; //noref
    private String nickname; //noref
    private Boolean isDisplayAvatar;
    private Integer birthday;
    private Integer friendCount;
    private Integer personalCardId;
}

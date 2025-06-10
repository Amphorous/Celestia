package org.hoyo.celestia.user.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DetailInfo {
    private Integer worldLevel;
    private PrivacySettingInfo privacySettingInfo;
    private String platformAccountId;
    private String platformNickname;
    private Integer headIcon;
    private String signature;
    private ArrayList<AvatarDetail> avatarDetailList;
    private String platform;
    private RecordInfo recordInfo;
    private String uid;
    private Integer level;
    private String nickname;
    private Boolean isDisplayAvatar;
    private Integer birthday;
    private Integer friendCount;
    private Integer personalCardId;
}

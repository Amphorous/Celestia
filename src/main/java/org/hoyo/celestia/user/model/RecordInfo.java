package org.hoyo.celestia.user.model;

import lombok.Data;

@Data
public class RecordInfo {
    private Integer achievementCount;
    private Integer bookCount;
    private Integer avatarCount;
    private Integer equipmentCount;
    private Integer musicCount;
    private Integer relicCount;
    private ChallengeInfo challengeInfo; //idk what ts is we need to figure it out
    private Integer maxRogueChallengeScore;
}

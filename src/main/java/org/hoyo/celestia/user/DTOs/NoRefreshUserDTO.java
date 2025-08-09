package org.hoyo.celestia.user.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hoyo.celestia.user.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoRefreshUserDTO {
    private String uid;
    private String nickname;
    private String signature;
    private String region; //get from uid
    private Integer headIcon; //headIcon
    private Integer achievementCount; //recordInfo.achievementCount
    private Integer level;

    public NoRefreshUserDTO(User user) {
        this.uid = user.getUid();
        this.nickname = user.getDetailInfo().getNickname();
        this.signature = user.getDetailInfo().getSignature();
        //region is manual
        this.headIcon = user.getDetailInfo().getHeadIcon();
        this.achievementCount = user.getDetailInfo().getRecordInfo().getAchievementCount();
        this.level = user.getDetailInfo().getLevel();
    }
}

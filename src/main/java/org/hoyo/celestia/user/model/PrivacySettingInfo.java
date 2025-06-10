package org.hoyo.celestia.user.model;

import lombok.Data;

@Data
public class PrivacySettingInfo {
    private Boolean displayCollection;
    private Boolean displayRecord;
    private Boolean displayRecordTeam;
    private Boolean displayOnlineStatus;
    private Boolean displayDiary;
}

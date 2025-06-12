package org.hoyo.celestia.subloaders.service;

import org.hoyo.celestia.user.model.AvatarDetail;
import org.hoyo.celestia.user.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubloaderService {
    public Boolean userSubloader(User user){
        if(!user.getDetailInfo().getPrivacySettingInfo().getDisplayCollection()){
            return false;
        }
        ArrayList<AvatarDetail> avatarDetailList = user.getDetailInfo().getAvatarDetailList();
        for(AvatarDetail character : avatarDetailList){
            
        }
    }
}

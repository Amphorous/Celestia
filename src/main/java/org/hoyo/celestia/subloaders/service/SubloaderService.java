package org.hoyo.celestia.subloaders.service;

import org.hoyo.celestia.user.model.AvatarDetail;
import org.hoyo.celestia.user.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubloaderService {
    public Boolean userSubloader(User user){
        if(!user.getDetailInfo().getPrivacySettingInfo().getDisplayCollection()){
            //users builds are private, return false
            return false;
        }
        ArrayList<AvatarDetail> avatarDetailList = user.getDetailInfo().getAvatarDetailList();
        for(AvatarDetail character : avatarDetailList){
            //make a build object for each character
            //-->
        }
        //this marks the end of subloading, having read the user builds
        return true;
    }
}

package org.hoyo.celestia.user.validate;

import org.springframework.stereotype.Service;

@Service
public class ValidateUid {
    public Boolean validate(String uid){
        return uid != null && uid.length() >= 5;
    }
}

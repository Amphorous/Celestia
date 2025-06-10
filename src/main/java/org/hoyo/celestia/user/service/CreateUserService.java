package org.hoyo.celestia.user.service;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import org.hoyo.celestia.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {

    public void fetchUser(String uid){
        FeignEnkaApiService feignService = Feign.builder()
                .decoder(new JacksonDecoder())
                .target(FeignEnkaApiService.class, "https://enka.network");

        try{
            User response = feignService.getPlayerData(uid);
            if(response != null){
                System.out.println(response.getTtl()+ " " + response.getUid());
                System.out.println(response.getDetailInfo().getWorldLevel()+ " " + response.getDetailInfo().getNickname()+ " " + response.getDetailInfo().getLevel());
                System.out.println(response.getDetailInfo().getAvatarDetailList().toString());
                System.out.println(response.getDetailInfo().getRecordInfo().toString());
            } else {
                System.out.println("Error");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

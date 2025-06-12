package org.hoyo.celestia.user.service;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import org.hoyo.celestia.timeouts.services.TimeoutService;
import org.hoyo.celestia.user.UserRepository;
import org.hoyo.celestia.user.model.User;
import org.hoyo.celestia.user.validate.ValidateUid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserService {

    private final ValidateUid validateUid;
    private final UserRepository userRepository;
    private final TimeoutService timeoutService;

    public CreateUserService(ValidateUid validateUid, UserRepository userRepository, TimeoutService timeoutService) {
        this.validateUid = validateUid;
        this.userRepository = userRepository;
        this.timeoutService = timeoutService;
    }

    //check mongo if user by uid exists
    //if no then enka call and create user
    //if yes then enka call and refresh data
    // [[ everytime a user refreshes a UID's data, this flow MUST occur, and the flow MUST go through here ]]
    public ResponseEntity<String> upsertUser(String uid){
        if(!validateUid.validate(uid)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad UID.");
        }
        if(!timeoutService.canIEnkaCallYet(uid)){
            return ResponseEntity.status(HttpStatus.OK).body("User is not enka call yet.");
        }

        Optional<User> userInDb = userRepository.findByUid(uid);
        if(userInDb.isPresent()){
            User user = userInDb.get();
            User newUser = getUser(uid);
            if(newUser == null || newUser.getDetailInfo() == null || newUser.getUid() == null){
                return ResponseEntity.status(HttpStatus.IM_USED).body("User found in DB but not in Enka.");
            }
            newUser.setId(user.getId());
            userRepository.save(newUser);
            //call subloader here---------

            //----------------------------
            return ResponseEntity.status(HttpStatus.OK).body("User updated successfully.");
        } else {
            User newUser = getUser(uid);
            if(newUser != null){
                userRepository.save(newUser);
                //call subloader here---------

                //----------------------------
                return ResponseEntity.status(HttpStatus.OK).body("User created successfully.");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
    }

    //this returns a user from enka or database (depending upon if the timout is satisfied) if uid is valid
    public User getUser(String uid){
        if (validateUid.validate(uid)){
            if(!timeoutService.canIEnkaCallYet(uid)){
                return userRepository.findByUid(uid).get();
            }
            FeignEnkaApiService feignService = Feign.builder()
                    .decoder(new JacksonDecoder())
                    .target(FeignEnkaApiService.class, "https://enka.network");

            try{
                User response = feignService.getPlayerData(uid);
                if(response != null){
                    timeoutService.upsertTimeoutByUID(uid);
                    return response;
                } else {
                    //System.err.println("Error in createUserService.fetchUser, could not get response from enka for uid " + uid);
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
        //System.err.println("Error in createUserService.fetchUser, could not get response from enka for uid " + uid);
        return null;
    }
}

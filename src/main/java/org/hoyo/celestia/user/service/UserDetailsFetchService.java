package org.hoyo.celestia.user.service;

import org.hoyo.celestia.user.DTOs.NoRefreshUserDTO;
import org.hoyo.celestia.user.model.User;
import org.hoyo.celestia.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsFetchService {

    private final UserRepository userRepository;
    private final CreateUserService createUserService;

    public UserDetailsFetchService(UserRepository userRepository, CreateUserService createUserService) {
        this.userRepository = userRepository;
        this.createUserService = createUserService;
    }

    //this refreshes if the user isnt in the DB, also region check function isnt here yet
    //this does not call subloader, so make sure to call upsert user on going to dashboard page
    /**
     * 1) on going to dashboard, request for builds
     * 1.5) if no builds are found, show that and show the full refresh option (at all times show the "show_builds boolean")
     * 2) after full refresh
     * */


    public String getRegionFromUid(String uid) {
        return switch (uid.charAt(0)) {
            case '0' -> "MHY";
            case '1', '2', '5' -> "CN";
            case '6' -> "NA";
            case '7' -> "EU";
            case '8' -> "ASIA";
            case '9' -> "THM";
            default -> "NONE";
        };
    }

    public ResponseEntity<NoRefreshUserDTO> getUserCardDetailsNoRefresh(String uid){

        if(!userRepository.existsByUid(uid)){
            User user = createUserService.getUser(uid);
            userRepository.save(user);
            NoRefreshUserDTO noRefreshUserDTO = new NoRefreshUserDTO(user);
            noRefreshUserDTO.setRegion(getRegionFromUid(uid));
            return ResponseEntity.ok(noRefreshUserDTO);
        }

        NoRefreshUserDTO noRefreshUserDTO = userRepository.findUserCardByUid(uid);
        noRefreshUserDTO.setRegion(getRegionFromUid(uid));
        return ResponseEntity.ok(noRefreshUserDTO);
    }

    //after a hard refresh,
    // true is returned if an update/insert occurs => if true is returned, frontend calls timeout and noRefresh
    // false otherwise => if false is returned, frontend calls timeout and if timeout < 0 the button is greyed
    public ResponseEntity<Boolean> getHardRefreshStatus(String uid){
        ResponseEntity<String> upsertResponse = createUserService.upsertUser(uid);
        if(upsertResponse.getStatusCode() == HttpStatus.OK && !Objects.equals(upsertResponse.getBody(), "User is not enka call yet.")){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}

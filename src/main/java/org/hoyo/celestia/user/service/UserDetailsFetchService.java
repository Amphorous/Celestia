package org.hoyo.celestia.user.service;

import org.hoyo.celestia.user.DTOs.NoRefreshUserDTO;
import org.hoyo.celestia.user.model.User;
import org.hoyo.celestia.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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


    public ResponseEntity<NoRefreshUserDTO> getUserCardDetailsNoRefresh(String uid){

        if(!userRepository.existsById(uid)){
            User user = createUserService.getUser(uid);
            NoRefreshUserDTO noRefreshUserDTO = new NoRefreshUserDTO(user);
            noRefreshUserDTO.setRegion("NONE");
            return ResponseEntity.ok(noRefreshUserDTO);
        }

        NoRefreshUserDTO noRefreshUserDTO = userRepository.findUserCardByUid(uid);
        noRefreshUserDTO.setRegion("NONE");
        return ResponseEntity.ok(noRefreshUserDTO);
    }
}

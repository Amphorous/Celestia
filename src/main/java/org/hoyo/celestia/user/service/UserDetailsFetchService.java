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

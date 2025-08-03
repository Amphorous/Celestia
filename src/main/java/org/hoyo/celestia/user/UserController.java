package org.hoyo.celestia.user;

import org.hoyo.celestia.user.DTOs.NoRefreshUserDTO;
import org.hoyo.celestia.user.service.CreateUserService;
import org.hoyo.celestia.user.service.UserDetailsFetchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final CreateUserService createUserService;
    private final UserDetailsFetchService userDetailsFetchService;

    public UserController(CreateUserService createUserService, UserDetailsFetchService userDetailsFetchService) {
        this.createUserService = createUserService;
        this.userDetailsFetchService = userDetailsFetchService;
    }

    @GetMapping("/{uid}")
    public ResponseEntity<String> createUser(@PathVariable String uid){
        return createUserService.upsertUser(uid);
    }

    @GetMapping("/dashboard/noRefresh/{uid}")
    public ResponseEntity<NoRefreshUserDTO> refreshUser(@PathVariable String uid){
        return userDetailsFetchService.getUserCardDetailsNoRefresh(uid);
    }
}

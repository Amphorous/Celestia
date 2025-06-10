package org.hoyo.celestia.user;

import org.hoyo.celestia.user.service.CreateUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final CreateUserService createUserService;

    public UserController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @GetMapping("/{uid}")
    public void createUser(@PathVariable String uid){
        createUserService.fetchUser(uid);
    }
}

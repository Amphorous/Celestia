package org.hoyo.celestia.fightprops;

import org.hoyo.celestia.fightprops.service.FightPropService;
import org.hoyo.celestia.user.model.AvatarDetail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fightprop")
public class FightPropController {

    private final FightPropService fightPropService;

    public FightPropController(FightPropService fightPropService) {
        this.fightPropService = fightPropService;
    }

    @PostMapping("/test")
    public void testFightProp(@RequestBody AvatarDetail character) {
        fightPropService.getFightPropNode(character);
    }
}

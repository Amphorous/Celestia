package org.hoyo.celestia.loaders;

import org.hoyo.celestia.loaders.service.WeaponLoaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/load")
public class LoaderController {
    private final WeaponLoaderService weaponLoaderService;

    public LoaderController(WeaponLoaderService weaponLoaderService) {
        this.weaponLoaderService = weaponLoaderService;
    }

    //load honker_meta.json
    //->load weapons
    //->load characters

    @GetMapping("/honker_weps")
    public ResponseEntity<String> loadHonkerWepsJSON() {
        return weaponLoaderService.execute();
    }
}

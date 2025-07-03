package org.hoyo.celestia.loaders;

import org.hoyo.celestia.loaders.global.GlobalMetaFileLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meta")
public class GlobalMetaFileController {

    private final GlobalMetaFileLoader globalMetaFileLoader;

    public GlobalMetaFileController(GlobalMetaFileLoader globalMetaFileLoader) {
        this.globalMetaFileLoader = globalMetaFileLoader;
    }

    @GetMapping("test")
    public void test() {
        System.out.println(globalMetaFileLoader.getMetaFile().getEquipmentSkill().get("24005").get("1").get("props").get("SpeedAddedRatio"));
    }
}

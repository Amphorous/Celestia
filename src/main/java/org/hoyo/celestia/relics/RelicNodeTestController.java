package org.hoyo.celestia.relics;

import org.hoyo.celestia.relics.service.CreateRelicService;
import org.hoyo.celestia.user.model.Relic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relic")
public class RelicNodeTestController {

    private final CreateRelicService createRelicService;

    public RelicNodeTestController(CreateRelicService createRelicService) {
        this.createRelicService = createRelicService;
    }

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestParam String uid, @RequestBody Relic relic) {
        createRelicService.createRelicNode(relic, uid);
        return ResponseEntity.ok("Success");
    }
}

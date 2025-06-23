package org.hoyo.celestia.loaders.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.hoyo.celestia.loaders.model.HonkerMetaObject;
import org.hoyo.celestia.loaders.model.HonkerWeaponObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Service
public class WeaponLoaderService {

    //read honker_weps.json
    //-if Weapons node doesn't exist, make it
    //iterate every wep id and relate it to weapon node using the parameters:
    //1. weapon_id
    //2. path
    //3. rarity
    // also add lvl 80 calcd stats using honker_meta.json

    public ResponseEntity<String> execute() {
        //check and create the weapon root node first
        rootCheckAndCreate();
        loadWeaponsFromFile();

        return null;
    }

    private void rootCheckAndCreate() {
        //check if root node Store:name=weapon exists, if not create one
        
    }

    public String loadWeaponsFromFile(){
        String honkerWepsPath = "src/main/java/org/hoyo/celestia/assets/honker_weps.json";
        String honkerMetaPath = "src/main/java/org/hoyo/celestia/assets/honker_meta.json";

        ObjectMapper mapper = JsonMapper.builder()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .build();

        JsonNode honkerWepsRootNode;
        JsonNode honkerMetaRootNode;
        try{
            honkerWepsRootNode = mapper.readTree(new File(honkerWepsPath));
            honkerMetaRootNode = mapper.readTree(new File(honkerMetaPath));
        } catch (IOException exception){
            exception.printStackTrace();
            return exception.getMessage();
        }

        Set<Map.Entry<String, JsonNode>> entries = honkerWepsRootNode.properties();
        HonkerMetaObject metaFile = mapper.convertValue(honkerMetaRootNode, HonkerMetaObject.class);
        //System.out.println(metaFile.getEquipment().get("20000").get("0").get("AttackAdd"));

        Iterator<Map.Entry<String, JsonNode>> entryIterator = entries.iterator();
        int count = 0;

        while(entryIterator.hasNext()){
            Map.Entry<String, JsonNode> entry = entryIterator.next();
            String weaponId = entry.getKey();
            JsonNode weaponNode = entry.getValue();

            HonkerWeaponObject weapon = mapper.convertValue(weaponNode, HonkerWeaponObject.class);
            //call a function which takes the meta obj and the weapon onj and makes a node for it
            weaponNodeCreator(weapon, metaFile, weaponId);
        }

        return null;
    }

    private void weaponNodeCreator(HonkerWeaponObject weapon, HonkerMetaObject metaFile, String weaponId) {
        //now create a pojo for the node and relationship which is supposed to go into the graph db
        //create repo methods for inserting the synthesized object
        //return
    }
}

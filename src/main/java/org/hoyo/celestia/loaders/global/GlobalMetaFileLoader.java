package org.hoyo.celestia.loaders.global;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.hoyo.celestia.loaders.model.HonkerMetaObject;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Data
@Component
public class GlobalMetaFileLoader {

    private HonkerMetaObject metaFile;

    @PostConstruct
    public void init() {
        ObjectMapper mapper = JsonMapper.builder()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .build();

        String honkerMetaPath = "src/main/java/org/hoyo/celestia/assets/honker_meta.json";
        JsonNode honkerMetaRootNode = null;
        try{
            honkerMetaRootNode = mapper.readTree(new File(honkerMetaPath));
        } catch (IOException exception){
            exception.printStackTrace();
        }

        metaFile = mapper.convertValue(honkerMetaRootNode, HonkerMetaObject.class);
    }
}

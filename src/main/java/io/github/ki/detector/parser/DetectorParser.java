package io.github.ki.detector.parser;

import io.github.ki.detector.enums.DetectorConstant;
import org.springframework.boot.origin.OriginTrackedValue;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DetectorParser {


    public static Map<String, String> parsePropertyMap(Map<String,Object> map){


        Map<String,Object> filteredMap = map.entrySet()
                .stream()
                .filter(e -> e.getKey().indexOf(DetectorConstant.PREFIX.getValue()) != -1)
                .collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));

        if(filteredMap.isEmpty())return Collections.EMPTY_MAP;
        Map<String, String> result = new HashMap<>();
        parseHibernateSyntax(filteredMap,result);

        return result;
    }

    private static void parseHibernateSyntax( Map<String,Object> srcMap,Map<String, String> descMap){
        OriginTrackedValue originTrackedValue = OriginTrackedValue.of(srcMap.get(DetectorConstant.HIBERNATE.getProperty()));

        if(originTrackedValue == null)return;
        if(originTrackedValue.getValue().toString() != Boolean.TRUE.toString())return;
        descMap.put("spring.jpa.hibernate.ddl-auto","create");
    }

}

package io.github.ki.detector;

import io.github.ki.detector.enums.DetectorConstant;
import org.springframework.boot.origin.OriginTrackedValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DetectorParser {


    public static Map<String, List<String>> parsePropertyMap(Map<String,Object> map){


        Map<String,Object> filteredMap = map.entrySet()
                .stream()
                .filter(e -> e.getKey().indexOf(DetectorConstant.PREFIX.getValue()) != -1)
                .collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));

        if(filteredMap.isEmpty())return null;
        Map<String, List<String>> result = new HashMap<>();
        parseHibernateSyntax(filteredMap,result);

        return result;
    }

    private static void parseHibernateSyntax( Map<String,Object> srcMap,Map<String, List<String>> descMap){
        OriginTrackedValue originTrackedValue = OriginTrackedValue.of(srcMap.get(DetectorConstant.HIBERNATE.getProperty()));

        if(originTrackedValue == null)return;
        if(originTrackedValue.getValue().toString() != Boolean.TRUE.toString())return;
        descMap.put("spring.jpa.hibernate.ddl-auto",List.of("create"));
    }

}

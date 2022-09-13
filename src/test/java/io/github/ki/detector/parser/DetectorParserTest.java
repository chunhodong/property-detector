package io.github.ki.detector.parser;

import io.github.ki.detector.enums.DetectorConstant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.origin.OriginTrackedValue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DetectorParserTest {

    @Test
    void 필드조회_빈값입력하면_빈값리턴(){

        Map map = DetectorParser.parsePropertyMap(Collections.EMPTY_MAP);

        assertThat(map.isEmpty()).isEqualTo(true);
    }

    @Test
    void 필드조회_hiberndate_ddlauto_감지필드없으면_빈값리턴(){

        Map map = DetectorParser.parsePropertyMap(Map.of("spring.jpa.hibernate.ddl-auto","create"));

        assertThat(map.isEmpty()).isEqualTo(true);
    }

    @Test
    void 필드조회_hiberndate_ddlauto_감지필드false면_빈값리턴(){

        Map propertyMap = Map.of("ki.detector.hibernate-ddlauto-deactive","false");
        Map map = DetectorParser.parsePropertyMap(propertyMap);

        assertThat(map.isEmpty()).isEqualTo(true);
    }

    @Test
    void 필드조회_hiberndate_ddlauto_감지필드true면_프로퍼티리턴(){

        Map propertyMap = Map.of("ki.detector.hibernate-ddlauto-deactive","true");
        Map map = DetectorParser.parsePropertyMap(propertyMap);
        assertThat(map.get("spring.jpa.hibernate.ddl-auto")).isNotNull();
        assertThat(map.get("spring.jpa.hibernate.ddl-auto")).isEqualTo("create");

    }

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

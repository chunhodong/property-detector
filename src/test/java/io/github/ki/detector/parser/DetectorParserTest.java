package io.github.ki.detector.parser;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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


}

package io.github.ki.detector.listener;

import io.github.ki.detector.parser.DetectorParser;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EnvironmentPrepareListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();



        Map<String,Object> propertyMap = getPropertySource(environment);

        if(propertyMap.isEmpty())return;

        Map<String, List<String>> detectedPropertyMap = DetectorParser.parsePropertyMap(propertyMap);

        if(detectedPropertyMap.isEmpty())return;

        detectedPropertyMap.entrySet().stream().forEach(e -> {
            Object object = propertyMap.get(e.getKey());
            if(object != null){
                boolean isContain = e.getValue().stream().anyMatch(s -> s.equals(object.toString()));
                if(isContain)throw new IllegalArgumentException("not allowed ddl-auto property");
            }
        });
    }

    private Map getPropertySource(ConfigurableEnvironment environment){

        return environment.getPropertySources()
                .stream()
                .filter(propertySource -> propertySource instanceof OriginTrackedMapPropertySource)
                .findFirst()
                .map(propertySource -> (Map)propertySource.getSource())
                .orElse(Collections.EMPTY_MAP);


    }
}

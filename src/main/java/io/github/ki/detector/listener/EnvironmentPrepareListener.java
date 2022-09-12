package io.github.ki.detector.listener;

import io.github.ki.detector.DetectorParser;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.List;
import java.util.Map;

public class EnvironmentPrepareListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();


        OriginTrackedMapPropertySource trackedMapPropertySource = (OriginTrackedMapPropertySource) environment.getPropertySources()
                .stream()
                .filter(propertySource -> propertySource instanceof OriginTrackedMapPropertySource)
                .findFirst().orElse(null);



        Map<String,Object> trackedPropertyMap = trackedMapPropertySource.getSource();
        Map<String, List<String>> map = DetectorParser.parsePropertyMap(trackedPropertyMap);
        if(map == null || map.isEmpty())return;;

        map.entrySet().stream().forEach(e -> {
            Object object = trackedPropertyMap.get(e.getKey());
            if(object != null){
                long count = e.getValue().stream().filter(s -> s.equals(object.toString())).count();
                if(count > 0)throw new IllegalArgumentException("restrict property value");
            }
        });
    }
}

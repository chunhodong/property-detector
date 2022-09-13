package io.github.ki.detector.listener;

import io.github.ki.detector.DetectorApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.Profiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class EnvironmentPrepareListenerTest {

    private EnvironmentPrepareListener target;

    private ApplicationEnvironmentPreparedEvent event;
    private ConfigurableBootstrapContext applicationContext;
    private Class<?> clazz;

    @BeforeEach
    void applicationContext준비(){

        applicationContext = new DefaultBootstrapContext();
        clazz = DetectorApplication.class;
        event = new ApplicationEnvironmentPreparedEvent(applicationContext,new SpringApplication(clazz),new String[0],getEnvironment());

    }

    @Test
    void hibernate_ddlauto_설정이create인경우_예외발생(){

        target = new EnvironmentPrepareListener();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() -> target.onApplicationEvent(event));
        assertThat(exception.getMessage()).isEqualTo("not allowed spring.jpa.hibernate.ddl-auto property value=create");
    }


    private ConfigurableEnvironment getEnvironment(){
        return new ConfigurableEnvironment() {
            @Override
            public void setActiveProfiles(String... profiles) {

            }

            @Override
            public void addActiveProfile(String profile) {

            }

            @Override
            public void setDefaultProfiles(String... profiles) {

            }

            @Override
            public MutablePropertySources getPropertySources() {
                String hibernateProps = "spring.jpa.hibernate.ddl-auto";
                String detectorPropos = "ki.detector.hibernate-ddlauto-deactive";
                MutablePropertySources mutablePropertySources = new MutablePropertySources();
                mutablePropertySources.addLast(new OriginTrackedMapPropertySource("TrackedList",Map.of(hibernateProps,"create",detectorPropos,"true")));
                return mutablePropertySources;
            }

            @Override
            public Map<String, Object> getSystemProperties() {
                return null;
            }

            @Override
            public Map<String, Object> getSystemEnvironment() {
                return null;
            }

            @Override
            public void merge(ConfigurableEnvironment parent) {

            }

            @Override
            public ConfigurableConversionService getConversionService() {
                return null;
            }

            @Override
            public void setConversionService(ConfigurableConversionService conversionService) {

            }

            @Override
            public void setPlaceholderPrefix(String placeholderPrefix) {

            }

            @Override
            public void setPlaceholderSuffix(String placeholderSuffix) {

            }

            @Override
            public void setValueSeparator(String valueSeparator) {

            }

            @Override
            public void setIgnoreUnresolvableNestedPlaceholders(boolean ignoreUnresolvableNestedPlaceholders) {

            }

            @Override
            public void setRequiredProperties(String... requiredProperties) {

            }

            @Override
            public void validateRequiredProperties() throws MissingRequiredPropertiesException {

            }

            @Override
            public String[] getActiveProfiles() {
                return new String[]{"test"};
            }

            @Override
            public String[] getDefaultProfiles() {
                return new String[]{"default"};
            }

            @Override
            public boolean acceptsProfiles(String... profiles) {
                return false;
            }

            @Override
            public boolean acceptsProfiles(Profiles profiles) {
                return false;
            }

            @Override
            public boolean containsProperty(String key) {
                return false;
            }

            @Override
            public String getProperty(String key) {
                return null;
            }

            @Override
            public String getProperty(String key, String defaultValue) {
                return null;
            }

            @Override
            public <T> T getProperty(String key, Class<T> targetType) {
                return null;
            }

            @Override
            public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
                return null;
            }

            @Override
            public String getRequiredProperty(String key) throws IllegalStateException {
                return null;
            }

            @Override
            public <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
                return null;
            }

            @Override
            public String resolvePlaceholders(String text) {
                return null;
            }

            @Override
            public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
                return null;
            }
        };
    }


}

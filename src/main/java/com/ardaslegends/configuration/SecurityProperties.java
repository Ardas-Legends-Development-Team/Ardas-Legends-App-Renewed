package com.ardaslegends.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "security")
@PropertySources({
        @PropertySource(value = "classpath:security-config.yml", factory = YamlPropertySourceFactory.class)
})
public class SecurityProperties {
    private Map<String, List<String>> roles;
}
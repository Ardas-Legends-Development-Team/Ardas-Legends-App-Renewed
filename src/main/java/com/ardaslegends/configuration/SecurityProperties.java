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

/**
 * Configuration properties for security settings.
 * This class is used to load security-related properties from the configuration file.
 * The properties are prefixed with "security" in the configuration file.
 * The configuration file is a YAML file located in the resources directory.
 */
@Setter
@Getter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "security")
@PropertySources({
        @PropertySource(value = "classpath:security-config.yml", factory = YamlPropertySourceFactory.class)
})
public class SecurityProperties {
    /**
     * A map of roles and their associated routes.
     * The key is the role name, and the value is a list of routes accessible by that role.
     */
    private Map<String, List<String>> roles;

    /**
     * Properties related to plugin access.
     */
    private PluginAccess pluginAccess;

    /**
     * Configuration properties for plugin access.
     * This class is used to load plugin access-related properties from the configuration file.
     */
    @Setter
    @Getter
    public static class PluginAccess {
        /**
         * The name of the header used for plugin access.
         */
        private String headerName;

        /**
         * The secret key used for plugin access.
         */
        private String secret;
    }
}
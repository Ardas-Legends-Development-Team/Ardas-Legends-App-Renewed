package com.ardaslegends.configuration;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.Properties;

/**
 * Factory for creating a {@link PropertySource} from a YAML resource.
 * This class implements {@link PropertySourceFactory} to support YAML files
 * as property sources in Spring's environment.
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    /**
     * Creates a {@link PropertySource} from the given YAML resource.
     *
     * @param name     the name of the property source (can be null)
     * @param resource the YAML resource to load
     * @return a {@link PropertySource} containing the properties from the YAML resource
     */
    @Override
    @NonNull
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());
        Properties properties = factory.getObject();
        assert properties != null;
        return new PropertiesPropertySource(Objects.requireNonNull(resource.getResource().getFilename()), properties);
    }
}
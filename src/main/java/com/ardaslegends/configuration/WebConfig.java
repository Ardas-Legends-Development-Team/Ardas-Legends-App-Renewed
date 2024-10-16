package com.ardaslegends.configuration;

import com.ardaslegends.configuration.converter.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for web-related settings.
 * This class implements {@link WebMvcConfigurer} to customize the configuration of Spring MVC.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Adds custom formatters to the {@link FormatterRegistry}.
     * This method registers converters for various enum types used in the application.
     *
     * @param registry the {@link FormatterRegistry} to add the converters to
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new RegionTypeEnumConverter());
        registry.addConverter(new ClaimbuildTypeEnumConverter());
        registry.addConverter(new ProductionSiteTypeEnumConverter());
        registry.addConverter(new SpecialBuildingEnumConverter());
        registry.addConverter(new ResourceTypeEnumConverter());
    }

    /**
     * Configures CORS mappings.
     * This method allows all origins to access the application.
     *
     * @param registry the {@link CorsRegistry} to add the mappings to
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}

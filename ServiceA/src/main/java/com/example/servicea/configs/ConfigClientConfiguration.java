package com.example.servicea.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
This class holds the custom value called 'external'
from the property file retrieved from the Config server
 */
@Component
@ConfigurationProperties(prefix="external") // Specify property file variable
@Getter
@Setter
public class ConfigClientConfiguration {

    private String property;
}

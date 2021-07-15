package com.example.servicea.controllers;

import com.example.servicea.configs.ConfigClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller demonstrates how an external configuration file retrieved
 * from the config server affects property values.
 */
/*
This lets the app update the @Value variables if the external global configuration changes!

Update the configuration file for this app in the configuration folder hosted on git. Then
call localhost:8100/refresh to update this apps configuration while its running.

Note: Calling localhost:8100/refresh is the manual way of updating the apps configuration.
You can make these calls happen automatically when a commit is detected on git for the config
folder by setting up /monitor with spring-cloud-config-monitor and spring-cloud-bus.
 */
@RefreshScope

@RestController
@RequestMapping("/config")
public class ConfigController {

    /**
     * This holds the value retrieved from the properties file that
     * was retrieved from the Config Server.
     */
    @Autowired
    private ConfigClientConfiguration externalProperty;

    /**
     * This holds the value retrieved from the properties file that
     * was retrieved from the Config Server.
     */
    @Value("${external.other.property}")
    private String externalOtherProperty;

    /**
     * This holds the value retrieved from the local properties file
     */
    @Value("${local.custom.property}")
    private String localProperty;



    /**
     * Gets the property file values and returns them as a string.
     * @return The internal and external property file values.
     */
    @GetMapping("/get-config-data")
    public String getConfigData(){

        // Used to build strings
        StringBuilder sb = new StringBuilder();

        // Add the properties
        sb.append(externalProperty.getProperty());
        sb.append(" || ");
        sb.append(externalOtherProperty);
        sb.append(" || ");
        sb.append(localProperty);

        // Return the property data as a string
        return sb.toString();
    }
}

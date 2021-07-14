package com.example.servicea.controllers;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/eureka")
public class EurekaController {


    // This is used to find services we can call by accessing the Eureka discovery server
    @Autowired
    private EurekaClient eClient;

    // This is an alternative to using 'EurekaClient'
    // It is a part of the Spring Discovery dependency
    // It can work with multiple types of discovery services, not just Eureka
    //@Autowired
    //private DiscoveryClient dClient;

    // This lets us call REST APIs for other services
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;



    // This calls the 'hidden-service' service using the Discovery Server to find an instance of the service
    @RequestMapping("/make-eureka-call")
    public String eurekaCall(){

        // Template for calling Rest services
        RestTemplate restTemplate = restTemplateBuilder.build();

        // Get the next available instance for 'hidden-service' from the Discovery server
        InstanceInfo instanceInfo = eClient.getNextServerFromEureka(
                "hidden-service",
                false);

        // Get the url of the service that we found
        String baseUrl = instanceInfo.getHomePageUrl();

        // Send a request to the service and get the response
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "simple/instance-info",
                HttpMethod.GET,
                null,
                String.class);

        // Return the response of the request we made
        return response.toString();
    }
}

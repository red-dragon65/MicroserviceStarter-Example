package com.example.servicea.controllers;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * NOTE: This is the same code as found in the 'EurekaController.java' class.
 * The difference is, this class uses a circuit breaker to protect its endpoints.
 */
@RestController
@RequestMapping("/breaker")
public class CircuitBreakerController {

    /**
     * This is used to find services we can call by accessing the Eureka discovery server.
     */
    @Autowired
    private EurekaClient eClient;

    /**
     * This lets us call REST APIs for other services.
     */
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;



    /**
     * This calls the 'hidden-service' service using the Discovery Server to find an instance of the service.
     * This method is also protected with a circuit breaker using a fallback method and a rate limiter.
     * @return The instance information about the 'hidden-service' instance that is called.
     */
    @CircuitBreaker(name="hiddenService", fallbackMethod="callFailure") // Specify fallback method in case of failure
    @RateLimiter(name="hiddenService") // Rate limit this call
    @RequestMapping("/eureka-protected-call")
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

    /**
     * This is a fallback method that will get called in case the 'eurekaCall' method
     * (/eureka-protected-call) runs into an exception.
     * @param e The exception thrown by the original method.
     * @return The default message to return if the original method fails.
     */
    public String callFailure(Exception e){

        return "Failed to connect to the 'hidden-service' endpoint...";
    }
}

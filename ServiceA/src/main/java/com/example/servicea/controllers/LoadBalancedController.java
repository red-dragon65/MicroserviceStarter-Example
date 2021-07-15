package com.example.servicea.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * This controller finds a 'hidden-service' service instance using client-side load balancing.
 * Then, it makes a call using either a 'RestTemplate', or a 'WebClient'.
 */
@RestController
@RequestMapping("/balanced")
public class LoadBalancedController {


    /*
    This is a basic rest template load balanced call
    -------------------------------------------------------
     */

    /**
     * Creates a load balanced REST template object used for
     * making REST calls.
     */
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    /**
     * This calls a 'hidden-service' instance using a client-side load balanced rest template.
     * @return The instance info retrieved from the 'hidden-service'.
     */
    @GetMapping("/make-rest-call")
    public String restCall(){

        // Send a request to the 'hidden-service' and get the response
        ResponseEntity<String> response = restTemplate.exchange("http://hidden-service/simple/instance-info", HttpMethod.GET, null, String.class);

        // Return the response of the REST endpoint we called
        return response.getBody();
    }

    /**
     * This defines a load balanced REST template object.
     * @return A load balanced RestTemplate object.
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    /*
    -------------------------------------------------------
     */







    /*
    This is a load balanced call using a reactive call
    -------------------------------------------------------
     */

    /**
     * Creates a load balanced WebClient.Builder object used
     * for making endpoint calls.
     */
    @Autowired
    @LoadBalanced
    private WebClient.Builder loadBalancedWebClientBuilder;

    // This calls the 'hidden-service' using a load balanced web client

    /**
     * This calls a 'hidden-service' instance using a client-side load balanced
     * WebClient.Builder object.
     * @return The instance info retrieved from the 'hidden-service'.
     */
    @GetMapping("/make-reactive-call")
    public Mono<String> reactiveCall(){

        // Make a load balanced call to the 'hidden-service' service
        Mono<String> response = loadBalancedWebClientBuilder.build().get()
                .uri("lb://hidden-service/simple/instance-info")
                .retrieve().bodyToMono(String.class);

        return response;
    }

    /**
     * Defines a load balanced WebClient.Builder.
     * @return A load balanced WebClient.Builder object.
     */
    @Bean
    @LoadBalanced
    WebClient.Builder builder() {
        return WebClient.builder();
    }

    /*
    -------------------------------------------------------
     */
}

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

@RestController
@RequestMapping("/balanced")
public class LoadBalancedController {


    /*
    This is a basic rest template load balanced call
    -------------------------------------------------------
     */

    // Create a load balanced REST template object
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    // This calls the 'hidden-service' using a load balanced rest template
    @GetMapping("/make-rest-call")
    public String restCall(){

        // Send a request to the 'hidden-service' and get the response
        ResponseEntity<String> response = restTemplate.exchange("http://hidden-service/simple/instance-info", HttpMethod.GET, null, String.class);

        // Return the response of the REST endpoint we called
        return response.getBody();
    }

    // Define a load balanced REST template object
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

    // Create a load balanced WebClient.Builder
    @Autowired
    @LoadBalanced
    private WebClient.Builder loadBalancedWebClientBuilder;

    // This calls the 'hidden-service' using a load balanced web client
    @GetMapping("/make-reactive-call")
    public Mono<String> reactiveCall(){

        // Make a load balanced call to the 'hidden-service' service
        Mono<String> response = loadBalancedWebClientBuilder.build().get()
                .uri("lb://hidden-service/simple/instance-info")
                .retrieve().bodyToMono(String.class);

        return response;
    }

    // Define a load balanced WebClient.Builder
    @Bean
    @LoadBalanced
    WebClient.Builder builder() {
        return WebClient.builder();
    }

    /*
    -------------------------------------------------------
     */
}

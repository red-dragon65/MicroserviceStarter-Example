package com.example.hiddenservice.controller;

import com.example.hiddenservice.model.InstanceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This is a simple controller mapping that is used by the 'ServiceA' service
@RestController
@RequestMapping("/simple")
public class SimpleController {

    // Get the runtime name set for this service from the .properties file
    @Value("${service.instance.name}")
    private String instanceName;


    // A dead simple REST mapping
    @RequestMapping("/hello")
    public String sayHello(){
        return "hello";
    }

    // An informative REST mapping
    @RequestMapping("instance-info")
    public InstanceDto getInfo(){

        InstanceDto infoDto = new InstanceDto("Hey","Bobby Joe", instanceName);

        return infoDto;
    }
}

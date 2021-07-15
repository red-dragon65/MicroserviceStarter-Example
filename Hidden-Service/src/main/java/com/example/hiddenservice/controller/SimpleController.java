package com.example.hiddenservice.controller;

import com.example.hiddenservice.model.InstanceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a simple controller mapping that is used by the 'ServiceA' service.
 */
@RestController
@RequestMapping("/simple")
public class SimpleController {

    /**
     * Gets the runtime environment variable that holds the name of the
     * service instance from the .properties file.
     */
    @Value("${service.instance.name}")
    private String instanceName;


    /**
     * A dead simple REST mapping that says 'hello'.
     * @return Returns a 'hello' string.
     */
    @RequestMapping("/hello")
    public String sayHello(){
        return "hello";
    }

    /**
     * A REST mapping that returns the name of the instance running.
     * @return An InstanceDto object containing the instances name.
     */
    @RequestMapping("/instance-info")
    public InstanceDto getInfo(){

        InstanceDto infoDto = new InstanceDto("Hey","Bobby Joe", instanceName);

        return infoDto;
    }
}

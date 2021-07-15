package com.example.hiddenservice.controller;

import com.example.hiddenservice.model.InstanceDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is a JUnit 5 integration test that tests the 'SimpleController' class endpoints.
 */

// Tests the controller endpoints
// Only starts up spring components needed for testing mvc code
@WebMvcTest(SimpleController.class)

// Note: JUnit 4 uses 'RunWith' instead of 'ExtendWith'
@ExtendWith(SpringExtension.class)
class SimpleControllerTest {

    /**
     * Ths is used to test endpoints.
     */
    @Autowired
    private MockMvc mvcTester;



    /**
     * Test the '/simple/hello' endpoint.
     */
    @Test
    void sayHello() throws Exception {

        // Set up the rest call
        String endpoint = "/simple/hello";
        RequestBuilder request = MockMvcRequestBuilders.get(endpoint);

        // Run the rest call
        MvcResult result = mvcTester.perform(request)
                .andDo(print())
                .andReturn();

        // Verify results
        assertEquals("hello", result.getResponse().getContentAsString());
    }

    /**
     * Test the '/simple/instance-info' endpoint.
     */
    @Test
    void getInfo() throws Exception {

        // Setup the expected value
        InstanceDto testDto = new InstanceDto();
        testDto.setMessage("Hey");
        testDto.setSenderName("Bobby Joe");
        testDto.setInstanceName("${INSTANCE_NAME}");

        // Set up the rest call
        String endpoint = "/simple/instance-info";
        RequestBuilder request = MockMvcRequestBuilders.get(endpoint);

        // Run the rest call and verify results
        mvcTester.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.message").value(testDto.getMessage()))
                .andExpect(jsonPath("$.senderName").value(testDto.getSenderName()))
                .andExpect(jsonPath("$.instanceName").value(testDto.getInstanceName()))
                .andExpect(status().isOk());
    }
}













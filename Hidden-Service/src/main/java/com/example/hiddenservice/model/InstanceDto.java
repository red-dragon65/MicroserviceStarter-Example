package com.example.hiddenservice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

// This is a simple DTO used to pass information about the service instance to the caller
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstanceDto {

    @NotNull
    private String message;

    @NotNull
    private String senderName;

    @NotNull
    private String instanceName;
}

package com.example.ticketshop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Actor request", description = "Required details needed to create a new Actor")
public class ActorRequest {
    @NotEmpty(message = "First name must not be empty!")
    @ApiModelProperty(value = "firstName", required = true, notes = "First name of the actor", example = "Victor")
    private String firstName;

    @NotEmpty(message = "Last name must not be empty!")
    @ApiModelProperty(value = "lastName", required = true, notes = "Last name of the actor", example = "Rebengiuc")
    private String lastName;

    @ApiModelProperty(value = "dateOfBirth", required = false, notes = "Date of birth of the actor", example = "1964-07-21")
    private Date dateOfBirth;

    @ApiModelProperty(value = "placeOfBirth", required = false, notes = "The place of birth", example = "Bucuresti")
    private String placeOfBirth;
}

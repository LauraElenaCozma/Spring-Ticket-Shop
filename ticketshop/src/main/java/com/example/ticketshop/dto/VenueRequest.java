package com.example.ticketshop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueRequest {
    @NotEmpty(message = "The venue name must not be null")
    @ApiModelProperty(value = "venueName", required = true, notes = "The name of the venue", example = "Sala Pictura")
    private String venueName;

    @NotEmpty(message = "The location must not be null")
    @ApiModelProperty(value = "locationName", required = true, notes = "The name of the building", example = "Teatrul National Bucuresti")
    private String locationName;

    @NotNull
    @Min(value = 10, message = "The capacity must be bigger than 10")
    @ApiModelProperty(value = "seatCapacity", required = true, notes = "The capacity of the venue", example = "120")
    private Integer seatCapacity;

}

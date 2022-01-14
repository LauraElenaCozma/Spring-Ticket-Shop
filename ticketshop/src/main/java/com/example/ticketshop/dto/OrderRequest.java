package com.example.ticketshop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotNull(message = "The event must not be null")
    @ApiModelProperty(value = "eventId", required = true, notes = "Event of the order", example = "1")
    private Long eventId;

    @NotNull(message = "The client must not be null")
    @ApiModelProperty(value = "clientId", required = true, notes = "Client of the order", example = "1")
    private Long clientId;

    @NotNull(message = "You must reserve a number of seats!")
    @Min(value = 1, message = "Minimum 1 seat reserved")
    @ApiModelProperty(value = "numReservedSeats", required = true, notes = "Number of reserved seats", example = "4")
    private Integer numReservedSeats;
}

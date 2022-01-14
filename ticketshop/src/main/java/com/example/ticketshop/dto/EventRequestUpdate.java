package com.example.ticketshop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestUpdate {
    @NotNull(message = "The venue must not be null")
    @ApiModelProperty(value = "venueId", required = true, notes = "Venue of the event", example = "1")
    private Long venueId;

    @NotNull(message = "The price must not be null")
    @Min(value = 0, message = "The price must have a positive value")
    @ApiModelProperty(value = "price", required = true, notes = "Venue of the event", example = "100")
    private Double price;

    @NotNull(message = "The date must not be null")
    @ApiModelProperty(value = "date", required = true, notes = "Date of the event", example = "2022-01-10")
    private Date date;

    @NotEmpty(message = "The hour must not be null")
    @ApiModelProperty(value = "hour", required = true, notes = "Hour of the event", example = "20:00")
    private String hour;
}

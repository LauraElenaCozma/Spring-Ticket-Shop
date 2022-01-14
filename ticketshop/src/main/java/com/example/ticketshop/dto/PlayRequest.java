package com.example.ticketshop.dto;

import com.example.ticketshop.model.Genre;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayRequest {
    @NotEmpty(message = "The name must not be empty!")
    @ApiModelProperty(value = "name", required = true, notes = "Name of the play", example = "Toti fiii mei")
    private String name;

    @ApiModelProperty(value = "author", required = false, notes = "Author of the play", example = "Arthur Miller")
    private String author;

    @ApiModelProperty(value = "director", required = false, notes = "Director of the play", example = "Ion Caramitru")
    private String director;

    @ApiModelProperty(value = "genre", required = false, notes = "Genre of the play", example = "COMEDY")
    private Genre genre;

    @NotNull(message = "The duration must not be null!")
    @Min(value = 0, message = "The duration must be a positive value!")
    @ApiModelProperty(value = "duration", required = true, notes = "Duration of the play", example = "90")
    private Double duration;

    @NotEmpty(message = "The actors must not be an empty array!")
    private List<Long> actorIds;
}

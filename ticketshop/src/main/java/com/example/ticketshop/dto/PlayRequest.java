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
import javax.validation.constraints.Size;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayRequest {
    @NotEmpty(message = "The name must not be empty!")
    @Size(max = 80, message = "The name of the play must not be longer than 80 characters")
    @ApiModelProperty(value = "name", required = true, notes = "Name of the play", example = "Toti fiii mei")
    private String name;

    @ApiModelProperty(value = "author", required = false, notes = "Author of the play", example = "Arthur Miller")
    @Size(max = 50, message = "The name of the author must not be longer than 50 characters")
    private String author;

    @ApiModelProperty(value = "director", required = false, notes = "Director of the play", example = "Ion Caramitru")
    @Size(max = 50, message = "The name of the director must not be longer than 50 characters")
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

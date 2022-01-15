package com.example.ticketshop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ActorResponse {
    private Long id;

    private String firstName;

    private String lastName;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;

    private String placeOfBirth;

}

package com.example.ticketshop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static com.example.ticketshop.dto.PatternPhoneNumber.PHONE_REGEX;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {
    @NotEmpty(message = "The first name must not be empty")
    @ApiModelProperty(value = "firstName", required = true, notes = "First name of the client", example = "Maria")
    private String firstName;

    @NotEmpty(message = "The last name must not be empty")
    @ApiModelProperty(value = "lastName", required = true, notes = "Last name of the client", example = "Popescu")
    private String lastName;

    @Email
    @Column(unique = true)
    @NotEmpty(message = "The email must not be empty")
    @ApiModelProperty(value = "email", required = true, notes = "Email of the client", example = "mariapopescu@gmail.com")
    private String email;

    @Pattern(regexp = PHONE_REGEX, message = "Invalid phone number")
    @Column(unique = true)
//    @ApiModelProperty(value = "phoneNumber", required = false, notes = "Phone number of the client", example = "0751178283")
    private String phoneNumber;
}

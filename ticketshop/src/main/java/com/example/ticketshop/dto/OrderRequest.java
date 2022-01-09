package com.example.ticketshop.dto;

import lombok.Builder;
import lombok.Data;


import javax.validation.constraints.NotNull;

@Data
@Builder
public class OrderRequest {
    @NotNull(message = "The event must not be null")
    private Long eventId;

    @NotNull(message = "The client must not be null")
    private Long clientId;
}

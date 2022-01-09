package com.example.ticketshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "seat_reservations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "row_num")
    private String rowNumber;

    @Column(name = "seat_num")
    private Integer seatNumber;

}

package com.example.ticketshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venues")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venue_id")
    private Long id;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();

    private String venueName;

    private String locationName;

    private Integer seatCapacity;

}

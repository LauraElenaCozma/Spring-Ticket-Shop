package com.example.ticketshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "actors")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Long id;

    @ManyToMany(mappedBy = "actors")
    private List<Play> plays;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String placeOfBirth;

}

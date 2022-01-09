package com.example.ticketshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "plays")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Play {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "play_id")
    private Long id;

    @OneToMany(mappedBy = "play", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "plays_actors",
            joinColumns = @JoinColumn(name = "play_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actors = new ArrayList<>();

    private String name;
    private String author;
    private String director;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private Double duration;

}

package com.example.ticketshop.repository;

import com.example.ticketshop.model.Play;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlayRepository extends JpaRepository<Play, Long> {
}

package com.example.ticketshop.repository;

import com.example.ticketshop.model.Client;
import com.example.ticketshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT ord FROM Client c " +
            "JOIN Order ord ON ord.client.id = c.id " +
            "WHERE c.id = :clientId AND YEAR(ord.orderDate) = :year")
    public List<Order> getAllOrdersByYear(@Param("clientId") Long clientId,
                                          @Param("year") Integer year);

    @Query("SELECT ord FROM Client c " +
            "JOIN Order ord ON ord.client.id = c.id " +
            "WHERE c.id = :clientId AND MONTH(ord.orderDate) = :month")
    public List<Order> getAllOrdersByMonth(@Param("clientId") Long clientId,
                                           @Param("month") Integer month);

    @Query("SELECT ord FROM Client c " +
            "JOIN Order ord ON ord.client.id = c.id " +
            "WHERE c.id = :clientId AND YEAR(ord.orderDate) = :year AND MONTH(ord.orderDate) = :month")
    public List<Order> getAllOrdersByYearAndMonth(@Param("clientId") Long clientId,
                                                  @Param("year") Integer year,
                                                  @Param("month") Integer month);

    public Optional<Client> findByEmail(String email);

    public Optional<Client> findByPhoneNumber(String phone);
}

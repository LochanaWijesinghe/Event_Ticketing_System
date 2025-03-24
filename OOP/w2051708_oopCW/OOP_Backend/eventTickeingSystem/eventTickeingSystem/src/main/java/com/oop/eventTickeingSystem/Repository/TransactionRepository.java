package com.oop.eventTickeingSystem.Repository;

import com.oop.eventTickeingSystem.model.Tickets;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Tickets, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Tickets t SET t.customerId = :newCustomerId WHERE t.customerId IS NULL")
    void assignNewCustomerIdToNullTickets(String newCustomerId);
}

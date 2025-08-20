package com.marianarocigno.pix_transfer_api.repository;

import com.marianarocigno.pix_transfer_api.model.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query("SELECT COALESCE(SUM(t.amount), 0) " +
            "FROM Transfer t" +
            "WHERE t.sender.id = :senderId" +
            "AND t.timestamp BETWEEN :startOfDay AND :endOfDay")
    BigDecimal sumTransfersForToday(@Param("senderId") Long senderId, @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

    default BigDecimal sumTransfersForToday(Long senderId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        return sumTransfersForToday(senderId, startOfDay, endOfDay);
    }
}

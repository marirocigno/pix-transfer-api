package com.marianarocigno.pix_transfer_api.repository;

import com.marianarocigno.pix_transfer_api.model.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransferRepository extends JpaRepository<Transfer, Long> {


    @Query("SELECT COALESCE(SUM(t.amount), 0) " +
            "FROM Transfer t " +
            "WHERE t.sender.id = :senderId " +
            "AND t.createdAt BETWEEN :startOfDay AND :endOfDay")
    BigDecimal sumTransfersForToday(@Param("senderId") Long senderId,
                                    @Param("startOfDay") LocalDateTime startOfDay,
                                    @Param("endOfDay") LocalDateTime endOfDay);
}

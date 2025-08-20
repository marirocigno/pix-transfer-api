package com.marianarocigno.pix_transfer_api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransferResponseDTO {
    private Long id;
    private String senderKey;
    private String receiverKey;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private BigDecimal senderBalanceAfter;
    private BigDecimal receiverBalanceAfter;
}

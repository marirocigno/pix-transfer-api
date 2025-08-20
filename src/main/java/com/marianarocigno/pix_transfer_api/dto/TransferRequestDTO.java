package com.marianarocigno.pix_transfer_api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDTO {
    private String senderKey;
    private String receiverKey;
    private BigDecimal amount;
}

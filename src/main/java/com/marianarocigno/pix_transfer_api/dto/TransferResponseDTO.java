package com.marianarocigno.pix_transfer_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferResponseDTO {
    private Long id;
    private String senderKey;
    private String receiverKey;
    private BigDecimal amount;
    private LocalDateTime createdAt;

    public TransferResponseDTO() {
    }

    public TransferResponseDTO(Long id, String senderKey, String receiverKey, BigDecimal amount, LocalDateTime createdAt) {
        this.id = id;
        this.senderKey = senderKey;
        this.receiverKey = receiverKey;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderKey() {
        return senderKey;
    }

    public void setSenderKey(String senderKey) {
        this.senderKey = senderKey;
    }

    public String getReceiverKey() {
        return receiverKey;
    }

    public void setReceiverKey(String receiverKey) {
        this.receiverKey = receiverKey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}

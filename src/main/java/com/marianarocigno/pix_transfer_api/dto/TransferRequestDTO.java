package com.marianarocigno.pix_transfer_api.dto;

import java.math.BigDecimal;

public class TransferRequestDTO {
    private String senderKey;
    private String receiverKey;
    private BigDecimal amount;

    public TransferRequestDTO() {
    }

    public TransferRequestDTO(String senderKey, String receiverKey, BigDecimal amount) {
        this.senderKey = senderKey;
        this.receiverKey = receiverKey;
        this.amount = amount;
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
}

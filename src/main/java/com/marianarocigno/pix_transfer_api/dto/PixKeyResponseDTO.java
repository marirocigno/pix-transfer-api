package com.marianarocigno.pix_transfer_api.dto;

import com.marianarocigno.pix_transfer_api.model.enums.PixKeyType;

import java.math.BigDecimal;

public class PixKeyResponseDTO {
    private Long id;
    private PixKeyType type;
    private String keyValue;
    private Long accountHolderId;
    private String accountHolderName;
    private BigDecimal accountHolderBalance;

    public PixKeyResponseDTO() {
    }

    public PixKeyResponseDTO(PixKeyType type, String keyValue, Long accountHolderId, String accountHolderName, BigDecimal accountHolderBalance) {
        this.type = type;
        this.keyValue = keyValue;
        this.accountHolderId = accountHolderId;
        this.accountHolderName = accountHolderName;
        this.accountHolderBalance = accountHolderBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PixKeyType getType() {
        return type;
    }

    public void setType(PixKeyType type) {
        this.type = type;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public Long getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(Long accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public BigDecimal getAccountHolderBalance() {
        return accountHolderBalance;
    }

    public void setAccountHolderBalance(BigDecimal accountHolderBalance) {
        this.accountHolderBalance = accountHolderBalance;
    }
}

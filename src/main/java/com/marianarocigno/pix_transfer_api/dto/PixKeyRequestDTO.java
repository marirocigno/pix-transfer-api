package com.marianarocigno.pix_transfer_api.dto;

import com.marianarocigno.pix_transfer_api.model.enums.PixKeyType;

public class PixKeyRequestDTO {
    private Long accountHolderId;
    private PixKeyType type;
    private String keyValue;

    public PixKeyRequestDTO() {
    }

    public PixKeyRequestDTO(Long accountHolderId, PixKeyType type, String keyValue) {
        this.accountHolderId = accountHolderId;
        this.type = type;
        this.keyValue = keyValue;
    }

    public Long getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(Long accountHolderId) {
        this.accountHolderId = accountHolderId;
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
}

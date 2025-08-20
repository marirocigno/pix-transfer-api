package com.marianarocigno.pix_transfer_api.dto;

import com.marianarocigno.pix_transfer_api.model.enums.PixKeyType;
import lombok.Data;

@Data
public class PixKeyRequestDTO {
    private Long accountHolderId;
    private PixKeyType type;
    private String keyValue;
}

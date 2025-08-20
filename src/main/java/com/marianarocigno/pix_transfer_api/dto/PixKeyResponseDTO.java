package com.marianarocigno.pix_transfer_api.dto;

import com.marianarocigno.pix_transfer_api.model.enums.PixKeyType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PixKeyResponseDTO {
    private Long id;
    private PixKeyType type;
    private String keyValue;
    private Long accountHolderId;
    private String accountHolderName;
    private BigDecimal accountHolderBalance;

}

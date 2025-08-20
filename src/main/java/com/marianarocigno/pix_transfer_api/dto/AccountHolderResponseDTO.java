package com.marianarocigno.pix_transfer_api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountHolderResponseDTO {

    private Long id;
    private String cpf;
    private String name;
    private String email;
    private String phone;
    private BigDecimal balance;
}

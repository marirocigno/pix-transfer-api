package com.marianarocigno.pix_transfer_api.dto;

import lombok.Data;

@Data
public class AccountHolderRequestDTO {
    private String cpf;
    private String name;
    private String email;
    private String phone;
}

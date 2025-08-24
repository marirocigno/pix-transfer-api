package com.marianarocigno.pix_transfer_api.dto;

import java.math.BigDecimal;

public class AccountHolderResponseDTO {

    private Long id;
    private String cpf;
    private String name;
    private String email;
    private String phone;
    private BigDecimal balance;

    public AccountHolderResponseDTO() {
    }

    public AccountHolderResponseDTO(String cpf, String name, String email, String phone, BigDecimal balance) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}

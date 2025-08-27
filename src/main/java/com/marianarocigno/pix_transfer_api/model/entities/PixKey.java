package com.marianarocigno.pix_transfer_api.model.entities;

import com.marianarocigno.pix_transfer_api.model.enums.PixKeyType;
import jakarta.persistence.*;

@Entity
public class PixKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String keyValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PixKeyType keyType;

    //muitas chaves pix (5) para uma conta
    @ManyToOne
    @JoinColumn(name = "account_holder_id")
    private AccountHolder accountHolder;

    public PixKey() {
    }

    public PixKey(String keyValue, PixKeyType keyType, AccountHolder accountHolder) {
        this.keyValue = keyValue;
        this.keyType = keyType;
        this.accountHolder = accountHolder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public PixKeyType getKeyType() {
        return keyType;
    }

    public void setKeyType(PixKeyType keyType) {
        this.keyType = keyType;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
    }
}

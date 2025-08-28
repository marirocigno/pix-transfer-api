package com.marianarocigno.pix_transfer_api.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Muias transferências podem ter o mesmo remetente.
    @ManyToOne
    private AccountHolder sender;

    // Muias transferências podem ter o mesmo destinatário.
    @ManyToOne
    private AccountHolder receiver;

    // Muitas transferências podem ser feitas com a mesma chave pix do remetente.
    @ManyToOne
    private PixKey senderPix;

    // Muitas transferências podem ser feitas com a mesma chave pix do destinatário.
    @ManyToOne
    private PixKey receiverPix;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Transfer() {
    }

    public Transfer(AccountHolder sender, AccountHolder receiver, BigDecimal amount, LocalDateTime createdAt) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountHolder getSender() {
        return sender;
    }

    public void setSender(AccountHolder sender) {
        this.sender = sender;
    }

    public AccountHolder getReceiver() {
        return receiver;
    }

    public void setReceiver(AccountHolder receiver) {
        this.receiver = receiver;
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

    public PixKey getSenderPix() {
        return senderPix;
    }

    public void setSenderPix(PixKey senderPix) {
        this.senderPix = senderPix;
    }

    public PixKey getReceiverPix() {
        return receiverPix;
    }

    public void setReceiverPix(PixKey receiverPix) {
        this.receiverPix = receiverPix;
    }
}
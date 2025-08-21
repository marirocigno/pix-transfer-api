package com.marianarocigno.pix_transfer_api.model.entities;

import com.marianarocigno.pix_transfer_api.model.enums.PixKeyType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "account_holder_id")
    private AccountHolder accountHolder;

}

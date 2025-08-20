package com.marianarocigno.pix_transfer_api.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class AccountHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    private BigDecimal balance = BigDecimal.ZERO;

    //uma conta para muitas chaves pix | todas as operações de salvar, etc, serão realizadas também na "classe filha" PixKeys | se uma chave for removida, tbm será removida do bd
    @OneToMany(mappedBy = "accountHolder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List pixKeys = new ArrayList<>();

}

package com.marianarocigno.pix_transfer_api.repository;

import com.marianarocigno.pix_transfer_api.model.entities.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
    Optional<AccountHolder> findByCpf(String cpf);
}

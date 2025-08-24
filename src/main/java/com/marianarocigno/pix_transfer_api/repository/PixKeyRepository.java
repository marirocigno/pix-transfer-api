package com.marianarocigno.pix_transfer_api.repository;

import com.marianarocigno.pix_transfer_api.model.entities.PixKey;
import com.marianarocigno.pix_transfer_api.model.enums.PixKeyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PixKeyRepository extends JpaRepository<PixKey, Long> {
    Optional<PixKey> findByKeyValue(String keyValue);

    boolean existsByAccountHolder_IdAndKeyType(Long accountHolderId, PixKeyType type);

    long countByAccountHolderId(Long accountHolderId);
}

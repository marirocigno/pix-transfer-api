package com.marianarocigno.pix_transfer_api.repository;

import com.marianarocigno.pix_transfer_api.model.entities.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface PixKeyRepository extends JpaRepository<PixKey, Long> {
    Optional<PixKey> findByKeyValue(String keyValue);

    List<PixKey> findByAccountHolderId(Long accountHolderId);

    long countByAccountHolderId(Long accountHolderId);
}

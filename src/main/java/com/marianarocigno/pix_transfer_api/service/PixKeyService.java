package com.marianarocigno.pix_transfer_api.service;

import com.marianarocigno.pix_transfer_api.dto.PixKeyRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.PixKeyResponseDTO;
import com.marianarocigno.pix_transfer_api.exception.BusinessException;
import com.marianarocigno.pix_transfer_api.model.entities.AccountHolder;
import com.marianarocigno.pix_transfer_api.model.entities.PixKey;
import com.marianarocigno.pix_transfer_api.model.enums.PixKeyType;
import com.marianarocigno.pix_transfer_api.repository.AccountHolderRepository;
import com.marianarocigno.pix_transfer_api.repository.PixKeyRepository;
import com.marianarocigno.pix_transfer_api.util.PixKeyValidator;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PixKeyService {

    @Autowired
    private final PixKeyRepository pixKeyRepository;

    @Autowired
    private final AccountHolderRepository accountHolderRepository;

    public PixKeyService(PixKeyRepository pixKeyRepository, AccountHolderRepository accountHolderRepository) {
        this.pixKeyRepository = pixKeyRepository;
        this.accountHolderRepository = accountHolderRepository;
    }

    public PixKeyResponseDTO create(PixKeyRequestDTO dto) {
        AccountHolder holder = accountHolderRepository.findById(dto.getAccountHolderId()).orElseThrow(() -> new ValidationException("Titular não encontrado."));

        if (dto.getType() != PixKeyType.RANDOM && pixKeyRepository.findByKeyValue(dto.getKeyValue()).isPresent()) {
            throw new BusinessException("Chave PIX já cadastrada.");
        }

        boolean hasSameType = pixKeyRepository.existsByAccountHolder_IdAndKeyType(holder.getId(), dto.getType());
        if (hasSameType) {
            throw new BusinessException("Titular já possui uma chave PIX do tipo.");
        }

        String keyValue = dto.getKeyValue();
        if (dto.getType() == PixKeyType.RANDOM)
            keyValue = PixKeyValidator.generateRandomKey();

        if (dto.getType() != PixKeyType.RANDOM)
            PixKeyValidator.isValid(keyValue, dto.getType());

        PixKey pixKey = new PixKey();
        pixKey.setKeyType(dto.getType());
        pixKey.setKeyValue(keyValue);
        pixKey.setAccountHolder(holder);

        long keysCount = pixKeyRepository.countByAccountHolderId(holder.getId());
        if (keysCount == 0 && holder.getBalance().signum() == 0) {
            holder.setBalance(BigDecimal.valueOf(100.00));
        }

        pixKeyRepository.save(pixKey);
        accountHolderRepository.save(holder);

         PixKeyResponseDTO response = new PixKeyResponseDTO();
         response.setId(pixKey.getId());
         response.setType(pixKey.getKeyType());
         response.setKeyValue(pixKey.getKeyValue());
         response.setAccountHolderId(holder.getId());
         response.setAccountHolderName(holder.getName());
         response.setAccountHolderBalance(holder.getBalance());

         return response;
    }
}
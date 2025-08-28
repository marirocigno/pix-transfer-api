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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PixKeyService {

    private final PixKeyRepository pixKeyRepository;

    private final AccountHolderRepository accountHolderRepository;

    public PixKeyService(PixKeyRepository pixKeyRepository, AccountHolderRepository accountHolderRepository) {
        this.pixKeyRepository = pixKeyRepository;
        this.accountHolderRepository = accountHolderRepository;
    }

    public PixKeyResponseDTO create(PixKeyRequestDTO dto) {
        AccountHolder holder = accountHolderRepository.findById(dto.getAccountHolderId()).orElseThrow(() -> new ValidationException("Titular não encontrado."));

        String keyValue;
        if (dto.getType() == PixKeyType.RANDOM) {
            keyValue = PixKeyValidator.generateRandomKey();
        }
        else {
            keyValue = dto.getKeyValue();
            PixKeyValidator.isValid(keyValue, dto.getType());
        }

        List<PixKey> currentKeys = pixKeyRepository.findByAccountHolderId(holder.getId());
        if (currentKeys.size() >= 5) {
            throw new BusinessException("O cliente já atingiu o limite máximo de 5 chaves Pix.");
        }

        for (PixKey k : currentKeys) {
            if (dto.getType() == PixKeyType.CPF && k.getKeyType() == PixKeyType.CPF) {
                throw new BusinessException("Já existe uma chave CPF cadastrada.");
            }
            if (dto.getType() == PixKeyType.EMAIL && k.getKeyType() == PixKeyType.EMAIL) {
                throw new BusinessException("Já existe uma chave de e-mail cadastrada.");
            }
            if (dto.getType() == PixKeyType.PHONE && k.getKeyType() == PixKeyType.PHONE) {
                throw new BusinessException("Já existe uma chave de telefone cadastrada.");
            }
        }

        PixKey pixKey = new PixKey();
        pixKey.setKeyType(dto.getType());
        pixKey.setKeyValue(keyValue);
        pixKey.setAccountHolder(holder);

        // só vai cair nesse cenário se for a primeira chave pix
        long keysCount = pixKeyRepository.countByAccountHolderId(holder.getId());
        if (keysCount == 0 && holder.getBalance().signum() == 0) {
            holder.setBalance(BigDecimal.valueOf(100.00));
        }

        pixKeyRepository.save(pixKey);
        accountHolderRepository.save(holder);

        return mapToDTO(pixKey);

    }

    public List<PixKeyResponseDTO> findAll() {
        List<PixKey> pixKeys = pixKeyRepository.findAll();
        List<PixKeyResponseDTO> dtos = new ArrayList<>();
        for (PixKey p : pixKeys) {
            dtos.add(mapToDTO(p));
        }
        return dtos;
    }

    public PixKeyResponseDTO findById(Long id) {
        PixKey pixKey = pixKeyRepository.findById(id).orElseThrow(() -> new BusinessException("Chave Pix não encontrada"));
        return mapToDTO(pixKey);
    }

    public void delete(Long id) {
        PixKey pixKey = pixKeyRepository.findById(id).orElseThrow(() -> new BusinessException("Chave Pix não encontrada"));
        pixKeyRepository.delete(pixKey);
    }

    private PixKeyResponseDTO mapToDTO(PixKey pixKey) {
        AccountHolder holder = pixKey.getAccountHolder();
        return new PixKeyResponseDTO(
                pixKey.getId(),
                pixKey.getKeyType(),
                pixKey.getKeyValue(),
                holder.getId(),
                holder.getName(),
                holder.getBalance()
        );
    }
}
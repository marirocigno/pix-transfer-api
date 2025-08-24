package com.marianarocigno.pix_transfer_api.service;

import com.marianarocigno.pix_transfer_api.dto.AccountHolderRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.AccountHolderResponseDTO;
import com.marianarocigno.pix_transfer_api.exception.BusinessException;
import com.marianarocigno.pix_transfer_api.model.entities.AccountHolder;
import com.marianarocigno.pix_transfer_api.model.enums.PixKeyType;
import com.marianarocigno.pix_transfer_api.repository.AccountHolderRepository;
import com.marianarocigno.pix_transfer_api.util.PixKeyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountHolderService {

    @Autowired
    private final AccountHolderRepository repository;

    public AccountHolderService(AccountHolderRepository repository) {
        this.repository = repository;
    }

    public AccountHolderResponseDTO create(AccountHolderRequestDTO dto) {
        if (repository.findByCpf(dto.getCpf()).isPresent()) {
            throw new BusinessException("CPF já cadastrado.");
        }

        PixKeyValidator.isValid(dto.getCpf(), PixKeyType.CPF);
        PixKeyValidator.isValid(dto.getEmail(), PixKeyType.EMAIL);
        PixKeyValidator.isValid(dto.getPhone(), PixKeyType.PHONE);

        AccountHolder holder = new AccountHolder();
        holder.setCpf(dto.getCpf());
        holder.setName(dto.getName());
        holder.setEmail(dto.getEmail());
        holder.setPhone(dto.getPhone());

        AccountHolder saved = repository.save(holder);

        AccountHolderResponseDTO response = new AccountHolderResponseDTO();
        response.setId(saved.getId());
        response.setCpf(saved.getCpf());
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());
        response.setPhone(saved.getPhone());
        response.setBalance(saved.getBalance());

        return response;

    }
}

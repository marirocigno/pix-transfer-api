package com.marianarocigno.pix_transfer_api.service;

import com.marianarocigno.pix_transfer_api.dto.AccountHolderRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.AccountHolderResponseDTO;
import com.marianarocigno.pix_transfer_api.exception.BusinessException;
import com.marianarocigno.pix_transfer_api.model.entities.AccountHolder;
import com.marianarocigno.pix_transfer_api.repository.AccountHolderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountHolderService {

    private final AccountHolderRepository repository;

    public AccountHolderService(AccountHolderRepository repository) {
        this.repository = repository;
    }

    public AccountHolderResponseDTO create(AccountHolderRequestDTO dto) {
        if (repository.findByCpf(dto.getCpf()).isPresent()) {
            throw new BusinessException("CPF já cadastrado.");
        }

        AccountHolder holder = new AccountHolder();
        holder.setCpf(dto.getCpf());
        holder.setName(dto.getName());
        holder.setEmail(dto.getEmail());
        holder.setPhone(dto.getPhone());

        repository.save(holder);

        return mapToDTO(holder);

    }

    public List<AccountHolderResponseDTO> findAll () {
        List<AccountHolder> holders = repository.findAll();
        List<AccountHolderResponseDTO> dtos = new ArrayList<>();
        for (AccountHolder h : holders) {
            dtos.add(mapToDTO(h));
        }
        return dtos;
    }

    public AccountHolderResponseDTO findById(Long id) {
        AccountHolder holder = repository.findById(id).orElseThrow(() -> new BusinessException("Titular não encontrado."));
        return mapToDTO(holder);
    }

    public AccountHolderResponseDTO update(Long id, AccountHolderRequestDTO dto) {
        AccountHolder holder = repository.findById(id).orElseThrow(() -> new BusinessException("Titular não encontrado."));
        holder.setName(dto.getName());
        holder.setEmail(dto.getEmail());
        holder.setPhone(dto.getPhone());

        repository.save(holder);

        return mapToDTO(holder);
    }

    public void delete(Long id) {
        AccountHolder holder = repository.findById(id).orElseThrow(() -> new BusinessException("Titular não encontrado."));
        repository.delete(holder);
    }

    //converte a entidade para DTO e centraliza a informação sem precisar repetir o código muitas vezes
    private AccountHolderResponseDTO mapToDTO(AccountHolder holder) {
        return new AccountHolderResponseDTO(
                holder.getId(),
                holder.getCpf(),
                holder.getName(),
                holder.getEmail(),
                holder.getPhone(),
                holder.getBalance()
        );
    }
}
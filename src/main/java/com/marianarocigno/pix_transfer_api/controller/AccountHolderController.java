package com.marianarocigno.pix_transfer_api.controller;

import com.marianarocigno.pix_transfer_api.dto.AccountHolderRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.AccountHolderResponseDTO;
import com.marianarocigno.pix_transfer_api.repository.AccountHolderRepository;
import com.marianarocigno.pix_transfer_api.service.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account-holders")
public class AccountHolderController {

    @Autowired
    private final AccountHolderService service;
    private final AccountHolderRepository repository;

    public AccountHolderController(AccountHolderService service, AccountHolderRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    public AccountHolderResponseDTO create(@RequestBody AccountHolderRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<AccountHolderResponseDTO> listAllDto() {
        return repository.findAll().stream().map(holder -> new AccountHolderResponseDTO(
                holder.getId(),
                holder.getCpf(),
                holder.getName(),
                holder.getEmail(),
                holder.getPhone(),
                holder.getBalance()

        )).toList();
    }
}

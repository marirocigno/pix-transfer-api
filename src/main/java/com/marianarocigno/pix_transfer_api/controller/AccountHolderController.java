package com.marianarocigno.pix_transfer_api.controller;

import com.marianarocigno.pix_transfer_api.dto.AccountHolderRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.AccountHolderResponseDTO;
import com.marianarocigno.pix_transfer_api.repository.AccountHolderRepository;
import com.marianarocigno.pix_transfer_api.service.AccountHolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account-holders")
@RequiredArgsConstructor
public class AccountHolderController {

    @Autowired
    private final AccountHolderService service;
    private final AccountHolderRepository repository;

    @PostMapping
    public AccountHolderResponseDTO create(@RequestBody AccountHolderRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<?> listAll() {
        return repository.findAll();
    }
}

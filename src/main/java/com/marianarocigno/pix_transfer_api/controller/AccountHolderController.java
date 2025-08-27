package com.marianarocigno.pix_transfer_api.controller;

import com.marianarocigno.pix_transfer_api.dto.AccountHolderRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.AccountHolderResponseDTO;
import com.marianarocigno.pix_transfer_api.service.AccountHolderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account-holders")
public class AccountHolderController {

    private final AccountHolderService service;

    public AccountHolderController(AccountHolderService service) {
        this.service = service;
    }

    @PostMapping
    public AccountHolderResponseDTO create(@RequestBody AccountHolderRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<AccountHolderResponseDTO> listAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AccountHolderResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public AccountHolderResponseDTO update(@PathVariable Long id, @RequestBody AccountHolderRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

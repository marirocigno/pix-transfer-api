package com.marianarocigno.pix_transfer_api.controller;

import com.marianarocigno.pix_transfer_api.dto.AccountHolderResponseDTO;
import com.marianarocigno.pix_transfer_api.dto.PixKeyRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.PixKeyResponseDTO;
import com.marianarocigno.pix_transfer_api.model.entities.PixKey;
import com.marianarocigno.pix_transfer_api.repository.PixKeyRepository;
import com.marianarocigno.pix_transfer_api.service.PixKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pix-keys")
public class PixKeyController {

    @Autowired
    private final PixKeyService service;

    @Autowired
    private final PixKeyRepository repository;

    public PixKeyController(PixKeyService service, PixKeyRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    public PixKeyResponseDTO create(@RequestBody PixKeyRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<PixKeyResponseDTO> listAllDto() {
        return repository.findAll().stream().map(key -> new PixKeyResponseDTO(
                key.getId(),
                key.getKeyType(),
                key.getKeyValue(),
                key.getAccountHolder().getId(),
                key.getAccountHolder().getName(),
                key.getAccountHolder().getBalance()
        )).toList();
    }
}


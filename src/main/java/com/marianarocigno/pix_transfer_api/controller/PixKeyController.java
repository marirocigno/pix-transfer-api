package com.marianarocigno.pix_transfer_api.controller;

import com.marianarocigno.pix_transfer_api.dto.PixKeyRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.PixKeyResponseDTO;
import com.marianarocigno.pix_transfer_api.repository.PixKeyRepository;
import com.marianarocigno.pix_transfer_api.service.PixKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pix-keys")
@RequiredArgsConstructor
public class PixKeyController {

    @Autowired
    private final PixKeyService service;

    @Autowired
    private final PixKeyRepository repository;

    @PostMapping
    public PixKeyResponseDTO create(@RequestBody PixKeyRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<?> listAll() {
        return repository.findAll();
    }
}


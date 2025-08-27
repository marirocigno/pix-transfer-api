package com.marianarocigno.pix_transfer_api.controller;

import com.marianarocigno.pix_transfer_api.dto.PixKeyRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.PixKeyResponseDTO;
import com.marianarocigno.pix_transfer_api.service.PixKeyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pix-keys")
public class PixKeyController {

    private final PixKeyService service;

    public PixKeyController(PixKeyService service) {
        this.service = service;
    }

    @PostMapping
    public PixKeyResponseDTO create(@RequestBody PixKeyRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<PixKeyResponseDTO> listAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PixKeyResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}


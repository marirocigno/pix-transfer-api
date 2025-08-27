package com.marianarocigno.pix_transfer_api.controller;

import com.marianarocigno.pix_transfer_api.dto.TransferRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.TransferResponseDTO;
import com.marianarocigno.pix_transfer_api.service.TransferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @PostMapping
    public TransferResponseDTO transfer(@RequestBody TransferRequestDTO dto) {
        return service.transfer(dto);
    }

    @GetMapping
    public List<TransferResponseDTO> listAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TransferResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

}
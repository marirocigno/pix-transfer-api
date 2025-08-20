package com.marianarocigno.pix_transfer_api.controller;

import com.marianarocigno.pix_transfer_api.dto.TransferRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.TransferResponseDTO;
import com.marianarocigno.pix_transfer_api.repository.TransferRepository;
import com.marianarocigno.pix_transfer_api.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

    @Autowired
    private TransferService service;

    @Autowired
    private TransferRepository repository;

    @PostMapping
    public TransferResponseDTO transfer(@RequestBody TransferRequestDTO dto) {
        return service.transfer(dto);
    }

    @GetMapping
    public List<?> listAll() {
        return repository.findAll();
    }
}

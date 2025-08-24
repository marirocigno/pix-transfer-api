package com.marianarocigno.pix_transfer_api.controller;

import com.marianarocigno.pix_transfer_api.dto.TransferRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.TransferResponseDTO;
import com.marianarocigno.pix_transfer_api.repository.TransferRepository;
import com.marianarocigno.pix_transfer_api.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
    private final TransferService service;

    @Autowired
    private final TransferRepository repository;

    public TransferController(TransferService service, TransferRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    public TransferResponseDTO transfer(@RequestBody TransferRequestDTO dto) {
        return service.transfer(dto);
    }

    @GetMapping
    public List<TransferResponseDTO> listAllDto() {
        return repository.findAll().stream().map(t -> new TransferResponseDTO(
                t.getId(),
                t.getSender().getPixKeys().getFirst().getKeyValue(),
                t.getReceiver().getPixKeys().getFirst().getKeyValue(),
                t.getAmount(),
                t.getCreatedAt(),
                t.getSender().getBalance(),
                t.getReceiver().getBalance()
        )).toList();
    }
}

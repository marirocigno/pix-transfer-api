package com.marianarocigno.pix_transfer_api.service;

import com.marianarocigno.pix_transfer_api.dto.TransferRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.TransferResponseDTO;
import com.marianarocigno.pix_transfer_api.exception.BusinessException;
import com.marianarocigno.pix_transfer_api.model.entities.AccountHolder;
import com.marianarocigno.pix_transfer_api.model.entities.PixKey;
import com.marianarocigno.pix_transfer_api.model.entities.Transfer;
import com.marianarocigno.pix_transfer_api.repository.AccountHolderRepository;
import com.marianarocigno.pix_transfer_api.repository.PixKeyRepository;
import com.marianarocigno.pix_transfer_api.repository.TransferRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransferService {

    private final PixKeyRepository pixKeyRepository;

    private final AccountHolderRepository accountHolderRepository;

    private final TransferRepository transferRepository;

    private static final BigDecimal DAILY_LIMIT = new BigDecimal("80.00");

    public TransferService(PixKeyRepository pixKeyRepository, AccountHolderRepository accountHolderRepository, TransferRepository transferRepository) {
        this.pixKeyRepository = pixKeyRepository;
        this.accountHolderRepository = accountHolderRepository;
        this.transferRepository = transferRepository;
    }

    public TransferResponseDTO transfer(TransferRequestDTO dto) {
        PixKey senderPix = pixKeyRepository.findByKeyValue(dto.getSenderKey()).orElseThrow(() -> new BusinessException("Chave Pix do remetente não encontrada."));
        PixKey receiverPix = pixKeyRepository.findByKeyValue(dto.getReceiverKey()).orElseThrow(() -> new BusinessException("Chave Pix do destinatário não encontrada."));

        AccountHolder sender = senderPix.getAccountHolder();
        AccountHolder receiver = receiverPix.getAccountHolder();

        // metodo compareTo -1 (a < b), 0 (a == b), 1 (b > a)
        if (sender.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new BusinessException("Saldo insuficiente para realizar a transferência");
        }

        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        BigDecimal totalToday = transferRepository.sumTransfersForToday(sender.getId(), startOfDay, endOfDay);

        // metodo compareTo -1 (a < b), 0 (a == b), 1 (b > a)
        if (totalToday.add(dto.getAmount()).compareTo(DAILY_LIMIT) > 0) {
            throw new BusinessException("Limite diário de R$ 80.00 excedido.");
        }

        sender.setBalance(sender.getBalance().subtract(dto.getAmount()));
        receiver.setBalance(receiver.getBalance().add(dto.getAmount()));
        accountHolderRepository.save(sender);
        accountHolderRepository.save(receiver);

        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        transfer.setReceiver(receiver);
        transfer.setSenderPix(senderPix);
        transfer.setReceiverPix(receiverPix);
        transfer.setAmount(dto.getAmount());
        transfer.setCreatedAt(LocalDateTime.now());

        Transfer saved = transferRepository.save(transfer);

        return mapToDTO(saved);
    }

    public List<TransferResponseDTO> findAll() {
        List<Transfer> transfers = transferRepository.findAll();
        List<TransferResponseDTO> dtos = new ArrayList<>();
        for (Transfer t : transfers) {
            dtos.add(mapToDTO(t));

        }
        return dtos;
    }

    public TransferResponseDTO findById(Long id) {
        Transfer transfer = transferRepository.findById(id).orElseThrow(() -> new BusinessException("Transferência não encontrada"));

        return mapToDTO(transfer);
    }

    private TransferResponseDTO mapToDTO(Transfer transfer) {
        TransferResponseDTO dto = new TransferResponseDTO();
        dto.setId( transfer.getId());
        dto.setSenderKey(transfer.getSenderPix().getKeyValue());
        dto.setReceiverKey(transfer.getReceiverPix().getKeyValue());
        dto.setAmount(transfer.getAmount());
        DateTimeFormatter formartter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        dto.setCreatedAt(transfer.getCreatedAt().format(formartter));

        return dto;
    }
}
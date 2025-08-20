package com.marianarocigno.pix_transfer_api.service;

import com.marianarocigno.pix_transfer_api.dto.TransferRequestDTO;
import com.marianarocigno.pix_transfer_api.dto.TransferResponseDTO;
import com.marianarocigno.pix_transfer_api.model.entities.AccountHolder;
import com.marianarocigno.pix_transfer_api.model.entities.PixKey;
import com.marianarocigno.pix_transfer_api.model.entities.Transfer;
import com.marianarocigno.pix_transfer_api.repository.AccountHolderRepository;
import com.marianarocigno.pix_transfer_api.repository.PixKeyRepository;
import com.marianarocigno.pix_transfer_api.repository.TransferRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class TransferService {

    @Autowired
    private PixKeyRepository pixKeyRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private TransferRepository transferRepository;

    private static final BigDecimal DAILY_LIMIT = new BigDecimal("80.00");

    public TransferResponseDTO transfer(TransferRequestDTO dto) {
        PixKey senderPix = pixKeyRepository.findByKeyValue(dto.getSenderKey()).orElseThrow(() -> new ValidationException("Chave PIX do remetente não encontrada."));
        PixKey receiverPix = pixKeyRepository.findByKeyValue(dto.getReceiverKey()).orElseThrow(() -> new ValidationException("Chave PIX do destinatário não encontrada."));

        AccountHolder sender = senderPix.getAccountHolder();
        AccountHolder receiver = receiverPix.getAccountHolder();

        if (sender.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new ValidationException("Saldo insuficiente para realizar a transferência");
        }

        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        BigDecimal totalToday = transferRepository.sumTransfersForToday(sender.getId(), startOfDay, endOfDay);
        if (totalToday == null) totalToday = BigDecimal.ZERO;

        if (totalToday.add(dto.getAmount()).compareTo(DAILY_LIMIT) > 0) {
            throw new ValidationException("Limite diário de R$ 80.00 excedido.");
        }

        sender.setBalance(sender.getBalance().subtract(dto.getAmount()));
        receiver.setBalance(receiver.getBalance().add(dto.getAmount()));

        accountHolderRepository.save(sender);
        accountHolderRepository.save(receiver);

        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        transfer.setReceiver(receiver);
        transfer.setAmount(dto.getAmount());
        transfer.setCreatedAt(LocalDateTime.now());

        Transfer saved = transferRepository.save(transfer);

        TransferResponseDTO response = new TransferResponseDTO();
        response.setId(saved.getId());
        response.setSenderKey(senderPix.getKeyValue());
        response.setReceiverKey(receiverPix.getKeyValue());
        response.setAmount(saved.getAmount());
        response.setCreatedAt(saved.getCreatedAt());
        response.setSenderBalanceAfter(sender.getBalance());
        response.setReceiverBalanceAfter(receiver.getBalance());

        return response;
    }
}

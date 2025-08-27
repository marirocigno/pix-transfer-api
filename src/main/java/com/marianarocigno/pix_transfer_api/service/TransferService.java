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

        //confirmar
        AccountHolder sender = senderPix.getAccountHolder();
        AccountHolder receiver = receiverPix.getAccountHolder();

        //subtrai o valor da quantidade no saldo e se o resultado for negativo, quer dizer que ele não possui saldo para fazer a transferência
        if (sender.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new BusinessException("Saldo insuficiente para realizar a transferência");
        }

        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        BigDecimal totalToday = transferRepository.sumTransfersForToday(sender.getId(), startOfDay, endOfDay);

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
        transfer.setAmount(dto.getAmount());
        transfer.setCreatedAt(LocalDateTime.now());

        Transfer saved = transferRepository.save(transfer);

        TransferResponseDTO response = new TransferResponseDTO();
        response.setId(saved.getId());
        response.setSenderKey(senderPix.getKeyValue());
        response.setReceiverKey(receiverPix.getKeyValue());
        response.setAmount(saved.getAmount());
        response.setCreatedAt(saved.getCreatedAt());

        return response;
    }
}

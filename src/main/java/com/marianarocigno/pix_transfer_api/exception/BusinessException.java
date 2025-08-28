package com.marianarocigno.pix_transfer_api.exception;

// Estende a RuntimeException por ser uma exceção não verificada, então o compilador não obriga a tratar com try/catch deixando o código das services mais limpo
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

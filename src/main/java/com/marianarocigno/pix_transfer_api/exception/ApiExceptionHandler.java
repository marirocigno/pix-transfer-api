package com.marianarocigno.pix_transfer_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//escuta o erro de todas as controllers tratando exceções lançadas em qualquer endpoint
@ControllerAdvice
public class ApiExceptionHandler {

    //"HTTP 400" erros de negócio: ex.: Saldo Insuficiente, assim o cliente consegue saber exatamente o que deu errado.
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusiness(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    //"HTTP 500" erros inesperados ou problemas de programação. Ex.: erro ao converter um JSON. Evita expor detalhes sensíveis do sistema.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error: Verifique se o JSON está correto. Caso o erro persista, entre em contato através da nossa Central de Atendimento.");
    }
}

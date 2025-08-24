package com.marianarocigno.pix_transfer_api.util;

import com.marianarocigno.pix_transfer_api.exception.BusinessException;
import com.marianarocigno.pix_transfer_api.model.enums.PixKeyType;

import java.util.UUID;
import java.util.regex.Pattern;

public class PixKeyValidator {

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{10,15}$");

    public static void isValid(String keyValue, PixKeyType type) {
        switch (type) {
            case CPF:
                if (!CPF_PATTERN.matcher(keyValue).matches()) {
                    throw new BusinessException("CPF inválido: deve conter apenas números. Ex: 14355012856");
            }
                break;
            case EMAIL:
                if (!EMAIL_PATTERN.matcher(keyValue).matches()) {
                    throw new BusinessException("E-mail inválido.");
                }
                break;
            case PHONE:
                if (!PHONE_PATTERN.matcher(keyValue).matches()) {
                    throw new BusinessException("Telefone inválido: deve conter apenas números. Ex: 11956539872");
                }
                break;
            case RANDOM:
                break;
            default:
                throw new BusinessException("Tipo de chave inválido.");
        }
    }

    public static String generateRandomKey() {
        return UUID.randomUUID().toString();
    }

}

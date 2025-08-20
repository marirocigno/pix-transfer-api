package com.marianarocigno.pix_transfer_api.util;

import com.marianarocigno.pix_transfer_api.model.enums.PixKeyType;

import java.util.UUID;
import java.util.regex.Pattern;

public class PixKeyValidator {

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{10,15}$");

    public static boolean isValid(String keyValue, PixKeyType type) {
        if (keyValue == null) return false;

        return switch (type) {
            case CPF -> CPF_PATTERN.matcher(keyValue).matches();
            case EMAIL -> EMAIL_PATTERN.matcher(keyValue).matches();
            case PHONE -> PHONE_PATTERN.matcher(keyValue).matches();
            case RANDOM -> true;
        };
    }

    public static String generateRandomKey() {
        return UUID.randomUUID().toString();
    }

}

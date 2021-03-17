package com.prvn.mobile.ws.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomStringGeneratorUtility {

    private final SecureRandom RANDOM  = new SecureRandom();
    private final String ALPHABET= "0123456789abvdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String generateUserId(int size){
        return generateRandomString(size);
    }

    private String generateRandomString(int size) {
        StringBuilder  builder = new StringBuilder(size);
        for (int i=0;i<size;i++){
           builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return builder.toString();
    }
}

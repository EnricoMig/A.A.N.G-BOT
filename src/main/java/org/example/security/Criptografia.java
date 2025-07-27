package org.example.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Criptografia {

    // Gera uma chave secreta AES
    private static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // Pode ser 128, 192 ou 256 bits
        return keyGen.generateKey();
    }

    // Criptografa uma string com AES
    private static String encrypt(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Descriptografa uma string com AES
    private static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    // Converte uma chave secreta para string Base64
    private static String keyToString(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    // Converte uma string Base64 de volta para chave secreta
    private static SecretKey stringToKey(String keyString) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(keyString);
            return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A chave fornecida não é válida em Base64.", e);
        }
    }

    public static String descriptografar(String encryptMessage, String secretKey) throws Exception {
        return decrypt(encryptMessage, stringToKey(secretKey));
    }

    public static String criptografar(String message) throws Exception {
        SecretKey key = generateKey();
        System.out.println(keyToString(key));
        return encrypt(message,key);

    }

}

package com.pichincha.prueba.config;

import java.io.UnsupportedEncodingException;
import java.security.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AES256 {

    @Value("${config.aes.initvector}")
    private String initVector;

    @Value("${config.aes.secret}")
    private String secret;

    private static final String ALGORITHM = "AES";
    private static final String TIPOALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * Obtiene un mensaje original a partir del cifrado.
     *
     * @param value cadena que se desea decrifrar
     * @return cadena decifrada
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws Exception
     */
    public String toMessage(String value) {
        Key key = generateKey();
        String dValue = "";
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            Cipher c = Cipher.getInstance(TIPOALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = c.doFinal(Base64.decodeBase64(value));
            dValue = new String(original);
            dValue = dValue.replaceAll("[\n\r]", "");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return dValue;

    }

    /**
     * Método que genera un AES256 a partir de un String
     *
     * @param _sinDescifrar String que se desea cifrar
     * @return Cadena de texto cifrada
     * @throws Exception
     */
    public String toAES256(String _sinDescifrar) {
        Key key = generateKey();
        String valueToEnc = null;
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            Cipher c = Cipher.getInstance(TIPOALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encrypted = c.doFinal(_sinDescifrar.getBytes());
            valueToEnc = Base64.encodeBase64String(encrypted);
            valueToEnc = valueToEnc.replaceAll("[\n\r]", "");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return valueToEnc;
    }

    /**
     * Generador de la clave con la que se encripta el mensaje
     *
     * @return
     * @throws Exception
     */
    private Key generateKey() {
        Key key = new SecretKeySpec(secret.getBytes(), ALGORITHM);
        return key;
    }

}

package com.guochuang.mimedia.tools;

import android.util.Base64;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


public class RSAHelper {
    private static final String RSA = "RSA";//AES 加密
    private static final String RSA_ANDROID = "RSA/ECB/PKCS1Padding";

    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;

        keyBytes = Base64.decode(key, Base64.DEFAULT);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decode(key, Base64.DEFAULT);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    public static String getKeyString(Key key)  {
        byte[] keyBytes = key.getEncoded();
        String s = Base64.encodeToString(keyBytes, Base64.DEFAULT);
        return s;
    }

    /**
     * 创建密钥对
     *
     * @return
     */
    public static KeyPair createKeyPair() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(RSA);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //密钥位数
        keyPairGen.initialize(1024);
        //密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();
        return keyPair;

    }

    public static String Encode(String content, PublicKey publicKey) throws Exception {
        //加解密类
        Cipher cipher = Cipher.getInstance(RSA_ANDROID);//Cipher.getInstance("RSA/ECB/PKCS1Padding");
        //明文
        byte[] plainText = content.getBytes(Charset.forName("UTF-8"));
        //加密
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] enBytes = cipher.doFinal(plainText);
        return Base64.encodeToString(enBytes, Base64.DEFAULT);
    }

    public static String Decode(String content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ANDROID);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] enBytes = Base64.decode(content, Base64.DEFAULT);
        byte[] deBytes = cipher.doFinal(enBytes);
        return new String(deBytes, Charset.forName("UTF-8"));
    }


}   
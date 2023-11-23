package com.naruto.didi2.util;


import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/11/25/0025
 */

public class AesUtils {
    private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private static final String AES = "AES";
    private static final String key = "zTrfC+zJL1fB5Pm8gXX9uueWuihz8IzSwagPsgFYbxs=";
    /**
     * 生成秘钥
     * @return Base64编码的秘钥
     */
    public static String generateSecretKey() {
        try {
            // 获取Key生成器实例,一般一个实例可以多次用来生成秘钥
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
            // 256位
            keyGenerator.init(256);
            // 生成密钥
            SecretKey secretKey = keyGenerator.generateKey();
            // 获取密钥
            byte[] keyBytes = secretKey.getEncoded();
            return Base64.encodeToString(keyBytes, Base64.DEFAULT);// 生成的秘钥转换成Base64编码,加、解密时需要用Base64还原秘钥
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     * @param plaintext 明文
     * @return Base64编码的密文
     */
    public static String encrypt(String plaintext) {
        try {
            // Base64还原秘钥
            byte[] keyBytes = Base64.decode(key.getBytes(), Base64.DEFAULT);
            // 还原密钥对象
            SecretKey secretKey = new SecretKeySpec(keyBytes, AES);
            // 加密初始化实例
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
            // CBC模式需要添加一个参数IvParameterSpec，ECB模式则不需要
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            byte[] result = cipher.doFinal(plaintext.getBytes("UTF-8"));
            return Base64.encodeToString(result, Base64.DEFAULT);// 生成的密文转换成Base64编码出文本,解密时需要用Base64还原出密文
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param ciphertext 密文
     * @return 明文
     */
    public static String decrypt(String ciphertext) {

        try {
            byte[] keyBytes = Base64.decode(key.getBytes(), Base64.DEFAULT);// Base64还原秘钥
            // 还原密钥对象
            SecretKey secretKey = new SecretKeySpec(keyBytes, AES);
            // 加密初始化实例
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            // Base64还原密文
            byte[] cipherBytes = Base64.decode(ciphertext, Base64.DEFAULT);
            byte[] result = cipher.doFinal(cipherBytes);
            return new String(result, "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

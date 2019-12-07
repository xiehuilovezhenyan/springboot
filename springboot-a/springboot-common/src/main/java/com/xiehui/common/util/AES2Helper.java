package com.xiehui.common.util;

import org.apache.commons.lang3.StringUtils;

import com.xiehui.common.core.exception.CustomException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AES2Helper {
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private static final String CODING = "utf-8";
    private static final Map<String, SecretKeySpec> SKEY = new ConcurrentHashMap<>();

    /**
     * 加密
     *
     * @param content 待加密内容
     * @param key key.getBytes(utf-8).length == 16 or 24 or 32 加密秘钥
     * @return 十六进制字符串
     */
    public static String encrypt(String content, String key) {
        if (null == content || content.equals("")) {
            return content;
        }
        final SecretKeySpec skey = genKey(key);
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            byte[] byteContent = content.getBytes(CODING);
            cipher.init(Cipher.ENCRYPT_MODE, skey);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return parseByte2HexStr(result); // 加密
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * 解密
     *
     * @param content 待解密内容(十六进制字符串)
     * @param key key.getBytes(utf-8).length == 16 or 24 or 32 加密秘钥
     * @return
     */
    public static String decrypt(String content, String key) {
        if (!isEncrpt(content)) {
            return content;
        }
        final SecretKeySpec skey = genKey(key);
        try {
            byte[] decryptFrom = parseHexStr2Byte(content);
            final Cipher cipher = Cipher.getInstance(TRANSFORMATION);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, skey);// 初始化
            byte[] result = cipher.doFinal(decryptFrom);
            return new String(result, CODING);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * 创建加密解密密钥
     *
     * @param key 加密解密密钥
     * @return
     */
    private static SecretKeySpec genKey(String key) {
        if (null != key) {
            int length = key.getBytes().length;
            if (length == 16 || length == 24 || length == 32) {
                if (SKEY.containsKey(key)) {
                    return SKEY.get(key);
                } else {
                    try {
                        SecretKeySpec skey = new SecretKeySpec(key.getBytes(CODING), ALGORITHM);
                        SKEY.put(key, skey);
                        return skey;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        throw new IllegalStateException(e.getMessage());
                    }
                }
            }
        }
        throw new IllegalStateException("the key must be accept key.getBytes('utf-8').length == 16 or 24 or 32");
    }

    public static boolean isEncrpt(String encrpt) {
        if (null != encrpt && !"".equals(encrpt)) {
            if (encrpt.length() % 32 == 0 && encrpt.matches("^[0-9abcdef]{32,}$")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte)(high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
      String data =   encrypt("{\"type\":1,\"uid\":\"123456\",\"outer_uid\":\"654321\",\"outer_order\":\"0123456789\",\"credit_status\":1,\"credit_amt\":1000,\"credit_time\":1557458832200}","4ce4cb8b68d343f48b7e0bbb19ac55db");
      System.out.println(data);

      String  desc = decrypt    ("5af83e6665e26395e7cf5ed47b275afda8243ed4c3bb28bd018bb7df17e48d53044ec111a5e86bf0e4ae692f636ecd168dba27af48c48396d5843a80e2bc313eb21019e5f4a7e76eae877910d538ba44789b829fdd3096ae7edb2b3b688d4f1b878298a3a278205a38f2afecf691b030e1e1763730eb171311a3a4d3df68bcbf","4ce4cb8b68d343f48b7e0bbb19ac55db");
        System.out.println(desc);
    }

    /**
     * 根据挖财规则签名
     *
     * @param method
     * @param appid
     * @param appSecretKey
     * @param body
     * @return
     * @throws CustomException
     */
    public static String getSignature(String method, String appid, String appSecretKey, String body) throws CustomException {
        String message;
        if (StringUtils.isBlank(body)) {
            message = method + '\n' + appid + '\n' + appSecretKey;
        } else {
            message = method + '\n' + body + '\n' + appid + '\n' + appSecretKey;
        }
        System.out.println(message);
        String signature;
        try {
            Mac hasher = Mac.getInstance("HmacSHA256");
            hasher.init(new SecretKeySpec(appSecretKey.getBytes(), "HmacSHA256"));
            byte[] hash = hasher.doFinal(message.getBytes());
            // 获得十六进制形式的签名
            signature = DatatypeConverter.printHexBinary(hash).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("签名校验异常");
        }
        return signature;
    }
}
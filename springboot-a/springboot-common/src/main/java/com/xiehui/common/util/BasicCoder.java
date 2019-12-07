package com.xiehui.common.util;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * 加密解密基类
 *
 * @author xiehui
 */
public class BasicCoder {
	private static final char[] HEXDIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	/**
	 * MD5(Message Digest algorithm 5，信息摘要算法)
	 *
	 * @param data 加密数据
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] makeMD5(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		mdTemp.update(data);
		return mdTemp.digest();
	}

	/**
	 * SHA(Secure Hash Algorithm，安全散列算法)
	 *
	 * @param data 加密数据
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws Exception
	 */
	public static byte[] makeSHA(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest sha = MessageDigest.getInstance("SHA");
		sha.update(data);
		return sha.digest();
	}

	/**
	 * HmacSHA1
	 *
	 * @param data 加密数据
	 * @param key  密钥
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @see Hmac
	 */
	public static byte[] makeHmacSHA1(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
		return makeHmac(Hmac.HmacSHA1, data, key);
	}

	/**
	 * HmacMD5
	 *
	 * @param data 加密数据
	 * @param key  密钥
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @see Hmac
	 */
	public static byte[] makeHmacMD5(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
		return makeHmac(Hmac.HmacMD5, data, key);
	}

	/**
	 * Hmac
	 *
	 * @param hmac 加密类型
	 * @param data 加密数据
	 * @param key  密钥
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @see Hmac
	 */
	public static byte[] makeHmac(Hmac hmac, byte[] data, byte[] key)
			throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance(hmac.toString());
		SecretKeySpec spec = new SecretKeySpec(key, hmac.toString());
		mac.init(spec);
		return mac.doFinal(data);
	}

	/**
	 * CRC32，循环冗余校验
	 *
	 * @param data 校验数据
	 * @return
	 */
	public static long makeCRC32(byte[] data) {
		CRC32 crc32 = new CRC32();
		crc32.update(data);
		return crc32.getValue();
	}

	/**
	 * 初始化HMAC密钥
	 *
	 * @return
	 * @throws Exception
	 * @see Hmac
	 */
	public static byte[] initMacKey(Hmac hmac) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(hmac.toString());
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * 将字节流按16进制转换成字符串.
	 */
	public static String byte2String(byte[] buf) {
		int count = buf.length;
		StringBuilder sbuf = new StringBuilder();
		for (int i = 0; i < count; i++) {
			byte byte0 = buf[i];
			sbuf.append(HEXDIGITS[byte0 >>> 4 & 0xf]).append(HEXDIGITS[byte0 & 0xf]);
		}
		return sbuf.toString();
	}

	/**
	 * Hmac(Hash Message Authentication Code，散列消息鉴别码)
	 *
	 * @author jianggujin
	 */
	public enum Hmac {
		HmacMD5, HmacSHA1, HmacSHA256, HmacSHA384, HmacSHA512
	}

}

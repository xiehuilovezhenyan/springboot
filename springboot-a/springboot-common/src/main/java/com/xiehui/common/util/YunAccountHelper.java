package com.xiehui.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

/**
 * 云账户签名算法
 *
 * @author xiehui
 */
public class YunAccountHelper {

	public static byte[] tripleDesEncrypt(byte[] content, byte[] key) throws GeneralSecurityException {
		byte[] icv = new byte[8];
		System.arraycopy(key, 0, icv, 0, 8);
		return tripleDesEncrypt(content, key, icv);
	}

	protected static byte[] tripleDesEncrypt(byte[] content, byte[] key, byte[] icv) throws GeneralSecurityException {
		final SecretKey secretKey = new SecretKeySpec(key, "DESede");
		final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		final IvParameterSpec iv = new IvParameterSpec(icv);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return cipher.doFinal(content);
	}

	public static byte[] tripleDesDecrypt(byte[] content, byte[] key) throws GeneralSecurityException {
		byte[] icv = new byte[8];
		System.arraycopy(key, 0, icv, 0, 8);
		return tripleDesDecrypt(content, key, icv);
	}

	protected static byte[] tripleDesDecrypt(byte[] content, byte[] key, byte[] icv) throws GeneralSecurityException {
		final SecretKey secretKey = new SecretKeySpec(key, "DESede");
		final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		final IvParameterSpec iv = new IvParameterSpec(icv);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		return cipher.doFinal(content);
	}

}

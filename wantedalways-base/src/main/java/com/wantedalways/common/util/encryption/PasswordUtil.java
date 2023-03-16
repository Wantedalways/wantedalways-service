package com.wantedalways.common.util.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 密码工具类
 * JAVA6支持以下任意一种算法 PBEWITHMD5ANDDES PBEWITHMD5ANDTRIPLEDES
 * 	 PBEWITHSHAANDDESEDE PBEWITHSHA1ANDRC2_40 PBKDF2WITHHMACSHA1
 * @author Wantedalways
 */
public class PasswordUtil {

    /**
     * 定义使用的算法为:PBEWithMD5andDES算法
     * 加密算法
     */
	public static final String ALGORITHM = "PBEWithMD5AndDES";

    /**
     * 定义使用的算法为:PBEWithMD5andDES算法
     * 密钥
     */
	public static final String SALT = "63293188";

	/**
	 * 定义迭代次数为1000次
	 */
	private static final int ITERATION_COUNT = 1000;

	/**
	 * 获取加密算法中使用的盐值,解密中使用的盐值必须与加密中使用的相同才能完成操作. 盐长度必须为8字节
	 * 
	 * @return byte[] 盐值
	 * */
	public static byte[] getSalt() throws Exception {
		// 实例化安全随机数
		SecureRandom random = new SecureRandom();
		// 产出盐
		return random.generateSeed(8);
	}

	public static byte[] getStaticSalt() {
		// 产出盐
		return SALT.getBytes();
	}

	/**
	 * 根据PBE密码生成一把密钥
	 * @param password 生成密钥时所使用的密码
	 * @return Key PBE算法密钥
	 * */
	private static Key getPbeKey(String password) {
		// 实例化使用的算法
		SecretKeyFactory keyFactory;
		SecretKey secretKey = null;
		try {
			keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			// 设置PBE密钥参数
			PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
			// 生成密钥
			secretKey = keyFactory.generateSecret(keySpec);

			return secretKey;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 加密明文字符串
	 * @param plaintext 待加密的明文字符串
	 * @param password 生成密钥时所使用的密码
	 * @param salt 盐值
	 * @return 加密后的密文字符串
	 */
	public static String encrypt(String plaintext, String password, String salt) {

		Key key = getPbeKey(password);
		byte[] encipheredData = null;
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt.getBytes(), ITERATION_COUNT);
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);

			cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
			encipheredData = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

			return bytesToHexString(encipheredData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 解密密文字符串
	 * @param ciphertext 待解密的密文字符串
	 * @param password 生成密钥时所使用的密码(如需解密,该参数需要与加密时使用的一致)
	 * @param salt 盐值(如需解密,该参数需要与加密时使用的一致)
	 * @return 解密后的明文字符串
	 */
	public static String decrypt(String ciphertext, String password, String salt) {

		Key key = getPbeKey(password);
		byte[] passDec = null;
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt.getBytes(), ITERATION_COUNT);
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);

			cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

			passDec = cipher.doFinal(hexStringToBytes(ciphertext));

			return new String(passDec);
		}

		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * @param src 字节数组
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length == 0) {
			return null;
		}
		for (byte b : src) {
			int v = b & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 将十六进制字符串转换为字节数组
	 * @param hexString 十六进制字符串
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || "".equals(hexString)) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}


}
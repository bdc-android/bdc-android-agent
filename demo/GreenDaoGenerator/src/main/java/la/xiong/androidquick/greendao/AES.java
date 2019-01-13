package la.xiong.androidquick.greendao;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES128 算法
 * CBC、ECB模式
 *PKCS7Padding、PKCS5Padding、ZeroBytePadding填充模式
 *CBC模式需要添加一个参数iv, ECB不需要参数iv
 * 介于java不支持PKCS7Padding和ZeroBytePadding，只支持PKCS5Padding 需要用到bouncycastle组件来实现
 */
public class AES {
	private static byte[] salt = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF };
	private static byte[] iv = {0x6c, 0x6c, 0x77, 0x61, 0x6e, 0x74, 0x61, 0x65, 0x73, 0x69, 0x76, 0x76, 0x31, 0x2e, 0x30, 0x31};
	private static byte[] password = {0x6c, 0x6c, 0x77, 0x61, 0x6e, 0x74, 0x61, 0x65, 0x73, 0x6b, 0x65, 0x79, 0x31, 0x2e, 0x30, 0x31};
	private final static String pwd = "llwantaeskey1.01";

	private final static int HASH_ITERATIONS = 10000;
	private final static int KEY_LENGTH = 128;
	// 算法名称
	private final static String KEY_ALGORITHM = "AES";
	private final static String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";

	// 加解密算法/模式/填充方式
	private final static String ALGORITHM_ZERO = "AES/CBC/ZeroBytePadding"; //ECB无iv
	private final static String ALGORITHM_PKCS5 = "AES/CBC/PKCS5Padding";
	private final static String ALGORITHM_PKCS7 = "AES/CBC/PKCS7Padding";

	/**
	 * AES加密
	 * ECB
	 * ZeroBytePadding
	 * @param content 要加密的字符串
	 * @return Base64 String
	 */
	public static String encrypt(String content){
		try{
			Security.addProvider(new BouncyCastleProvider());
			SecretKeySpec skeySpec = new SecretKeySpec(password, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM_ZERO, "BC");//"算法/模式/补码方式"
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv));
			byte[] encryptByte = cipher.doFinal(content.getBytes());
			String encryptString = new String(Base64.encode(encryptByte));
			return encryptString;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * AES加密
	 * ECB
	 * ZeroBytePadding
	 * @param content 要加密的byte[]
	 * @return byte[]
	 */
	public static byte[] encrypt(byte[] content){
		try{
			Security.addProvider(new BouncyCastleProvider());
			SecretKeySpec skeySpec = new SecretKeySpec(password, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM_ZERO, "BC");//"算法/模式/补码方式"
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv));
			byte[] encryptByte = cipher.doFinal(content);
			return encryptByte;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * AES解密
	 * ECB
	 * ZeroBytePadding
	 * @param content 要解密的字符串
	 * @return String
	 */
	public static String decrypt(String content){
		try {
			Security.addProvider(new BouncyCastleProvider());
			SecretKeySpec skeySpec = new SecretKeySpec(password, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM_ZERO, "BC");//"算法/模式/补码方式"
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));
			byte[] encrypted = Base64.decode(content);//先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * AES解密
	 * ECB
	 * ZeroBytePadding
	 * @param content 要解密的byte[]
	 * @return byte[]
	 */
	public static byte[] decrypt(byte[] content){
		try {
			Security.addProvider(new BouncyCastleProvider());
			SecretKeySpec skeySpec = new SecretKeySpec(password, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM_ZERO, "BC");//"算法/模式/补码方式"
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));
			byte[] original = cipher.doFinal(content);
			return original;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * AES加密
	 * CBC
	 * PKCS5Padding
	 * @param content 要加密的字符串
	 * @return Base64 String
	 */
	public static String encryptPKCS5(String content){
		try{
			SecretKeySpec skeySpec = new SecretKeySpec(pwd.getBytes(), KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM_PKCS5);//"算法/模式/补码方式"
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv));
			byte[] encryptByte = cipher.doFinal(content.getBytes());
			String encryptString = new String(Base64.encode(encryptByte));
			return encryptString;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * AES解密
	 * CBC
	 * PKCS5Padding
	 * @param content 要解密的字符串
	 * @return Base64 String
	 */
	public static String decryptPKCS5(String content){
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(pwd.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM_PKCS5);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));
			byte[] encrypByte = Base64.decode(content);//先用base64解密
			byte[] original = cipher.doFinal(encrypByte);
			String originalString = new String(original);
			return originalString;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	public static Key getSecreteKey() {
		try {
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
			PBEKeySpec myKeyspec = new PBEKeySpec(new String(pwd).toCharArray(), salt, HASH_ITERATIONS, KEY_LENGTH);
			SecretKey sk = keyfactory.generateSecret(myKeyspec);
			byte[] keyBytes = sk.getEncoded();
			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
			int base = 16;
			if (keyBytes.length % base != 0) {
				int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
				keyBytes = temp;
			}
			// 转化成JAVA的密钥格式
			Key key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
			return key;
		} catch (Exception nsae) {
			nsae.printStackTrace();
		}
		return null;
	}

	/**
	 * AES加密
	 * CBC
	 * PKCS7Padding
	 * @param content 要加密的byte[]
	 * @return byte[]
	 */
	public static byte[] encryptPKCS7(byte[] content) {
		byte[] encryptedByte = null;
		try {
			//Key key = getSecreteKey();
			Security.addProvider(new BouncyCastleProvider());
			SecretKeySpec skeySpec = new SecretKeySpec(password, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM_PKCS7, "BC");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv));
			encryptedByte = cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedByte;
	}

	/**
	 * AES加密
	 * CBC
	 * PKCS7Padding
	 * @param content 要加密的字符串
	 * @return Base64 String
	 */
	public static String encryptPKCS7(String content) {
		byte[] encrypt = encryptPKCS7(content.getBytes());
		byte[] encode = Base64.encode(encrypt);
		return new String(encode);
	}

	/**
	 * AES解密
	 * CBC
	 * PKCS7Padding
	 * @param content 要解密的byte[]
	 * @return byte[]
	 */
	public static byte[] decryptPKCS7(byte[] content) {
		byte[] decrypteByte = null;
		try {
			//Key key = getSecreteKey();
			Security.addProvider(new BouncyCastleProvider());
			SecretKeySpec skeySpec = new SecretKeySpec(password, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM_PKCS7, "BC");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));
			decrypteByte = cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decrypteByte;
	}

	/**
	 * AES解密
	 * CBC
	 * PKCS7Padding
	 * @param content 要解密的字符串
	 * @return String
	 */
	public static String decryptPKCS7(String content) {
		byte[] decode = Base64.decode(content);
		byte[] decrypt = decryptPKCS7(decode);
		return new String(decrypt);
	}

	public static String decryptResponse(String content){
		String result = null;
		//String str = new String(Base64.decode(new String(Base64.decode(content))));

		String str = "wLEdNwykR+TrNhFf9e/2GQ==::1388140163541925";
		String substring = str.substring(0, str.indexOf("::"));

		result = decryptResponse(b("2018102000000001").substring(0,16), str.substring(str.indexOf("::") + 2), Base64.decode(substring.getBytes()));

		return result;
	}

	public static String decryptResponse(String str, String str2, byte[] bArr){
		Security.addProvider(new BouncyCastleProvider());
		String str3 = "";
		Key secretKeySpec = new SecretKeySpec(str.getBytes(), "AES");
		try {
			Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
			instance.init(2, secretKeySpec, new IvParameterSpec(str2.getBytes()));
			return new String(instance.doFinal(bArr), "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return str3;
		} catch (NoSuchPaddingException e2) {
			e2.printStackTrace();
			return str3;
		} catch (BadPaddingException e3) {
			e3.printStackTrace();
			return str3;
		} catch (IllegalBlockSizeException e4) {
			e4.printStackTrace();
			return str3;
		} catch (InvalidKeyException e5) {
			e5.printStackTrace();
			return str3;
		} catch (InvalidAlgorithmParameterException e6) {
			e6.printStackTrace();
			return str3;
		} catch (UnsupportedEncodingException e7) {
			e7.printStackTrace();
			return str3;
		} catch (Exception e8) {
			e8.printStackTrace();
			return str3;
		}

	}

	public static void main(String[] args) throws Exception {
//		String json = "{\"users\":[{\"userId\":1,\"nickName\":\"admin\",\"tel\":\"123456\",\"birthday\":\"Jul 21, 2015 12:00:00 AM\",\"sex\":\"1\",\"realName\":\"test啊\",\"status\":0,\"passwd\":\"123\",\"areaId\":1},{\"userId\":2,\"nickName\":\"alex\",\"tel\":\"18687513927\",\"birthday\":\"Jun 2, 1983 12:00:00 AM\",\"sex\":\"\",\"realName\":\"龙达\",\"status\":0,\"passwd\":\"1\",\"areaId\":1}],\"msg\":\"成功\"}";
//		System.out.println("password: \n" + new String(password));
//		System.out.println("iv: \n" + new String(iv));
//
//		//ZeroBytePadding
//		String encrypt1 = AES.encrypt(json);
//		String decode1 = AES.decrypt(encrypt1);
//		System.out.println("ZeroBytePadding 加密: \n" + encrypt1);
//		System.out.println("ZeroBytePadding 解密: \n" + decode1);
//
//		//PKCS5Padding
//		String encrypt2 = AES.encryptPKCS5(json);
//		String decode2 = AES.decryptPKCS5(encrypt2);
//		System.out.println("PKCS5Padding 加密: \n" + encrypt2);
//		System.out.println("PKCS5Padding 解密: \n" + decode2);
//
//		//PKCS7Padding
//		String encrypt3 = AES.encryptPKCS7(json);
//		String decode3 = AES.decryptPKCS7(encrypt3);
//		System.out.println("PKCS7Padding 加密: \n" + encrypt3);
//		System.out.println("PKCS7Padding 解密: \n" + decode3);
		String cccc = "ZDB4RlpFNTNlV3RTSzFSeVRtaEdaamxsTHpKSFVUMDlPam94TXpnNE1UUXdNVFl6TlRReE9USTE";
		String bbbb = decryptResponse(cccc);
		System.out.println("PKCS7Padding 解密: \n" + bbbb);
		//String result = "F2tfdtQMTP/30PTcAMXf3QZannaLzvjGsCN7ztsx5aWxMkO1Hg6CkfRJtYdosrY4i8FvqoH/4IuZQSctMi4n40M0lov1ToceYFZ9SCLpInpkiEBS7jNxKMUOUjUcxXKF1BHcErRALzdvTIrsHCXvhln0V77qKvEe18BjwM=::R3X5gVPL15ReAtxG";


		//		Base64.decode(result.getBytes());
//		String aaa = b("201810200000000001").substring(0,16);
//
//		String decode5 = AES.decryptPKCS5(encrypt2);
//		try {
//			SecretKeySpec skeySpec = new SecretKeySpec(aaa.getBytes(), "AES");
//			Cipher cipher = Cipher.getInstance(ALGORITHM_PKCS5);
//			cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec("3".getBytes()));
//			byte[] encrypByte = Base64.decode(result);//先用base64解密
//			byte[] original = cipher.doFinal(encrypByte);
//			String originalString = new String(original);
//			System.out.println("PKCS5Padding 解密originalString: \n" + decode2);
//		} catch (Exception ex) {
//			System.out.println(ex.toString());
//		}
	}

	public static String b(String str) {
		byte[] bArr = null;
		try {
			MessageDigest instance = MessageDigest.getInstance("SHA-1");
			instance.update(str.getBytes());
			bArr = instance.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return a(bArr);
	}

	public static String a(byte[] bArr) {
		String str = "";
		str = "";
		for (byte b : bArr) {
			String toHexString = Integer.toHexString(b & 255);
			str = toHexString.length() == 1 ? str + "0" + toHexString : str + toHexString;
		}
		return str;
	}

}

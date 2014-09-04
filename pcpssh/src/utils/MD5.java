package utils;

import java.security.MessageDigest;

/**
 * 
 * @author song
 *
 */
public class MD5 {

	/**
	 * 
	 * Function : 加密指定字符�?
	 * 
	 * @author : song
	 * @param s
	 *            : 被加密参�?
	 * @return : 加密后的结果
	 */
	public static final String getMd5(String s) {
		char hexDigits[] = {
				'1', '7', '3', '4', '6', '1', '9', '3', '2', 's', 'o', 'n',
				'g', 'y', 'i', 'n', 'g', 'j', 'i', 'a', 'n'
				 };
		try {
			char str[];
			byte strTemp[] = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte md[] = mdTemp.digest();
			int j = md.length;
			str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}

			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(MD5.getMd5("admin"));
	}
}

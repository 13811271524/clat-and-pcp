import java.io.ByteArrayInputStream;

public class ByteAndString {
	// method1
	public static String getBinaryStrFromByte(byte b) {
		String result = "";
		byte a = b;
		;
		for (int i = 0; i < 8; i++) {
			byte c = a;
			a = (byte) (a >> 1);
			a = (byte) (a << 1);
			if (a == c) {
				result = "0" + result;
			} else {
				result = "1" + result;
			}
			a = (byte) (a >> 1);
		}
		return result;
	}

	// method2
	public static String byteToBit(byte b) {
		return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
				+ (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
				+ (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
				+ (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
	}

	public static byte decodeBinaryString(String byteStr) {
		int re, len;
		if (null == byteStr) {
			return 0;
		}
		len = byteStr.length();
		if (len != 4 && len != 8) {
			return 0;
		}
		if (len == 8) {// 8 bit
			if (byteStr.charAt(0) == '0') {//
				re = Integer.parseInt(byteStr, 2);
			} else {//
				re = Integer.parseInt(byteStr, 2) - 256;
			}
		} else {// 4 bit
			re = Integer.parseInt(byteStr, 2);
		}
		return (byte) re;
	}

	// important,worth to learn,16jinzhi to 16jinzhistring
	public static String hextohexstring(byte b) {
		String s = "";
		//b to unsigned int,very important
		byte[] a = { b };
		ByteArrayInputStream in = new ByteArrayInputStream(a);
		int i = in.read();
		// important function,xiao xie zi mu
		s = Integer.toHexString(i);
		if (s.length() == 1) {
			s = "0" + s;
		}
		return s;
	}
	
	//
}

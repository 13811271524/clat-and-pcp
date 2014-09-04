import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.*;

public class Server {

	static String mCurrentAddr = null;
	static int mCurrentPort = 0;
	static String mCurrent = null;
	static DatagramSocket ds = null;
	static byte[] tempArray;
	//
	static Mysql mysql;
	static Connection connectionsql;
	static java.sql.Statement statementsql;
	//
	static byte b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13,
			b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, b25, b26,
			b27, b28, b29, b30, b31, b32, b33, b34, b35, b36, b37, b38, b39,
			b40, b41, b42, b43, b44, b45, b46, b47, b48, b49, b50, b51, b52,
			b53, b54, b55, b56, b57, b58, b59, b60, b61, b62, b63, b64, b65,
			b66, b67, b68, b69, b70, b71, b72, b73, b74, b75, b76, b77, b78;
	static byte[] b79, b80, b81, b82, b83;
	//
	static byte[] fixlength = new byte[2];
	static byte[] prefixsuffix = new byte[12];

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		Server server = new Server();
		ServerRecvThreadbz recvThread = server.new ServerRecvThreadbz();
		recvThread.start();
		//
		mysql = new Mysql(connectionsql, statementsql);
		mysql.connectmysql();
	}

	class ServerRecvThreadbz extends Thread {
		byte[] buffer;
		byte[] data;

		public void run() {
			// for test

			try {

				ds = new DatagramSocket(5351);

				while (true) {
					buffer = new byte[1024];
					DatagramPacket dataPacket = new DatagramPacket(buffer,
							buffer.length);
					ds.receive(dataPacket);

					mCurrentAddr = dataPacket.getAddress().getHostAddress();
					mCurrentPort = dataPacket.getPort();
					mCurrent = mCurrentAddr + "+"
							+ String.valueOf(mCurrentPort);
					System.out.println("yuan di zhi he duan kou: " + mCurrent);

					data = dataPacket.getData();
					int dataLen = dataPacket.getLength();
					tempArray = new byte[dataLen];
					System.arraycopy(data, 0, tempArray, 0, dataLen);

					// for (int i = 0; i < dataLen; i++) {
					// String str = getBinaryStrFromByte(tempArray[i]);
					// System.out.println(str);
					// }
					// System.out.println("ling yige hanshu :");
					for (int i = 0; i < dataLen; i++) {
						String str = ByteAndString.byteToBit(tempArray[i]);
						// System.out.println(str);
					}

					if (tempArray[0] == 0) {
					} else {
						gouzaoqianmian();
						jiequprefixsuffix();
						gouzaoprefixsuffix();

					}

					buffer = null;
					data = null;
					tempArray = null;
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public final static void response(String addr, int port, byte[] outbuffer)
			throws IOException {

		DatagramPacket dp = new DatagramPacket(outbuffer, outbuffer.length,
				InetAddress.getByName(addr), port);
		dp.setData(outbuffer);
		ds.send(dp);
		dp = null;
	}

	public static String returnfixstring(byte[] fixlength,
			byte[] Prefix64andSuffix) {
		String Prefix64string = "";
		//
		int length = Integer.valueOf(fixlength[0]) * 256
				+ Integer.valueOf(fixlength[1]);
		String lengthstr = String.valueOf(length * 8);
		// prefix+suffix=96bits,96/8=12
		String[] str = new String[12];
		for (int i = 0; i < 12; i++) {
			// use a important function
			str[i] = ByteAndString.hextohexstring(Prefix64andSuffix[i]);
		}
		Prefix64string = str[0] + str[1] + ":" + str[2] + str[3] + ":" + str[4]
				+ str[5] + ":" + str[6] + str[7] + ":" + str[8] + str[9] + ":"
				+ str[10] + str[11] + ":0000:0000/" + lengthstr;
		return Prefix64string;
	}

	public static void gouzaoqianmian() {
		// Version(8bits)
		b0 = (tempArray[0]);
		// R and Opcode(8bits)
		String str1 = ByteAndString.byteToBit(tempArray[1]);
		str1 = "1" + str1.substring(1, 8);
		b1 = ByteAndString.decodeBinaryString(str1);
		// Reserved(8bits)
		b2 = (byte) 0;
		// Result Code
		b3 = (byte) 0;// 0 means success;
		// Lifitime(32bits)
		b4 = (byte) 0;
		b5 = (byte) 0;
		b6 = (byte) 0x0e;
		b7 = (byte) 0x10;
		// Epoch Time(32bits)
		b8 = (byte) 0;
		b9 = (byte) 0;
		b10 = (byte) 0;
		b11 = (byte) 0;
		// Reserved(96bits)
		b12 = (byte) 0;
		b13 = (byte) 0;
		b14 = (byte) 0;
		b15 = (byte) 0;
		b16 = (byte) 0;
		b17 = (byte) 0;
		b18 = (byte) 0;
		b19 = (byte) 0;
		b20 = (byte) 0;
		b21 = (byte) 0;
		b22 = (byte) 0;
		b23 = (byte) 0;
		// (optional) Opcode-specific response data
		// Map(288bits)
		b24 = (byte) tempArray[24];
		b25 = (byte) tempArray[25];
		b26 = (byte) tempArray[26];
		b27 = (byte) tempArray[27];
		b28 = (byte) tempArray[28];
		b29 = (byte) tempArray[29];
		b30 = (byte) tempArray[30];
		b31 = (byte) tempArray[31];
		b32 = (byte) tempArray[32];
		b33 = (byte) tempArray[33];
		b34 = (byte) tempArray[34];
		b35 = (byte) tempArray[35];
		b36 = (byte) tempArray[36];
		b37 = (byte) tempArray[37];
		b38 = (byte) tempArray[38];
		b39 = (byte) tempArray[39];
		b40 = (byte) tempArray[40];
		b41 = (byte) tempArray[41];
		b42 = (byte) tempArray[42];
		b43 = (byte) tempArray[43];
		b44 = (byte) tempArray[44];
		b45 = (byte) tempArray[45];
		b46 = (byte) tempArray[46];
		b47 = (byte) tempArray[47];
		b48 = (byte) tempArray[48];
		b49 = (byte) tempArray[49];
		b50 = (byte) tempArray[50];
		b51 = (byte) tempArray[51];
		b52 = (byte) tempArray[52];
		b53 = (byte) tempArray[53];
		b54 = (byte) tempArray[54];
		b55 = (byte) tempArray[55];
		b56 = (byte) tempArray[56];
		b57 = (byte) tempArray[57];
		b58 = (byte) tempArray[58];
		b59 = (byte) tempArray[59];
	}

	public static void gouzaoprefixsuffix() {

		// mysql chaxun to determine (optional)IPv4 Prefix List (Variable)
		int lengthint = Integer.valueOf(fixlength[0]) * 256
				+ Integer.valueOf(fixlength[1]);
		if (lengthint == 12) {
			// huo qu qian zhui geshu
			String[] array = null;
			array = mysql.searchall();
			String[] qianzhuigeshu = qiuchujigeprefix(array);
			// mei ge qian zhui fa yi ge bao
			for (int i = 0; i < qianzhuigeshu.length; i++) {
				byte[] qianzhuis = jiequqianzhui(qianzhuigeshu[i]);

				// (optional) PCP Options
				// Option Code(8bits)
				b60 = (byte) 129;
				// Reserved(8bits)
				b61 = (byte) 0;
				// Option Length(16bits) (in octets)
				b62 = (byte) 0;
				b63 = (byte) 0x6;
				// Prefix64 Length(16bits) (in octets),56/8=7
				b64 = qianzhuis[0];
				b65 = qianzhuis[1];
				// Prefix64+Suffix=96(bits)? 96/8=12bytes
				// Prefix64 (Variable)
				b66 = qianzhuis[2];
				b67 = qianzhuis[3];
				b68 = qianzhuis[4];
				b69 = qianzhuis[5];
				b70 = qianzhuis[6];
				b71 = qianzhuis[7];
				b72 = qianzhuis[8];
				// Suffix (Variable)
				b73 = qianzhuis[9];
				b74 = qianzhuis[10];
				b75 = qianzhuis[11];
				b76 = qianzhuis[12];
				b77 = qianzhuis[13];

				String[] sarray = null;
				sarray = mysql.search(qianzhuigeshu[i]);
				// IPv4 Prefix Count(8bits)
				b78 = (byte) sarray.length;
				b79 = new byte[sarray.length];
				b80 = new byte[sarray.length];
				b81 = new byte[sarray.length];
				b82 = new byte[sarray.length];
				b83 = new byte[sarray.length];
				for (int j = 0; j < sarray.length; j++) {
					System.out.println(sarray[j]);
					// IPv4 Prefix Length(8bits)
					int prefixlength = Integer
							.valueOf(sarray[j].substring(
									sarray[j].lastIndexOf("/") + 1,
									sarray[j].length()));
					b79[j] = (byte) prefixlength;
					// IPv4 Address (32 bits)
					String ipv4string = sarray[j].substring(
							sarray[j].indexOf(",") + 1,
							sarray[j].lastIndexOf("/"));
					// yi "." fen ge shi,bu neng xie cheng ".",yao xie cheng "\\."
					String[] k = ipv4string.split("\\.");
					int[] m = new int[4];
					for (int n = 0; n < 4; n++) {
						m[n] = Integer.valueOf(k[n]);
					}

					System.out.println(m[0] + " " + m[1] + " " + m[2] + " "
							+ m[3] + " " + prefixlength);

					b80[j] = (byte) m[0];
					b81[j] = (byte) m[1];
					b82[j] = (byte) m[2];
					b83[j] = (byte) m[3];
				}
				// gou zao wan zheng bao ,fa song shu ju
				byte[] bytesToSendqm = { b0, b1, b2, b3, b4, b5, b6, b7, b8,
						b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19,
						b20, b21, b22, b23, b24, b25, b26, b27, b28, b29, b30,
						b31, b32, b33, b34, b35, b36, b37, b38, b39, b40, b41,
						b42, b43, b44, b45, b46, b47, b48, b49, b50, b51, b52,
						b53, b54, b55, b56, b57, b58, b59, b60, b61, b62, b63,
						b64, b65, b66, b67, b68, b69, b70, b71, b72, b73, b74,
						b75, b76, b77, b78 };
				byte[] bytesToSendfix = new byte[5 * b79.length];
				for (int j = 0; j < b79.length; j++) {
					bytesToSendfix[5 * j] = b79[j];
					bytesToSendfix[5 * j + 1] = b80[j];
					bytesToSendfix[5 * j + 2] = b81[j];
					bytesToSendfix[5 * j + 3] = b82[j];
					bytesToSendfix[5 * j + 4] = b83[j];
				}
				byte[] bytesToSend = new byte[79 + 5 * b79.length];
				System.arraycopy(bytesToSendqm, 0, bytesToSend, 0, 79);
				System.arraycopy(bytesToSendfix, 0, bytesToSend, 79,
						5 * b79.length);
				// ipv6 ke yi zhi ding duan kou wang hui fa, ipv4 bu xing
				// response(mCurrentAddr, mCurrentPort, bytesToSend);
				try {
					response(mCurrentAddr, 5350, bytesToSend);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bytesToSend = null;

			}
		} else {// (optional) PCP Options
			// Option Code(8bits)
			b60 = (byte) 129;
			// Reserved(8bits)
			b61 = (byte) 0;
			// Option Length(16bits) (in octets)
			b62 = (byte) 0;
			b63 = (byte) 0x6;
			// Prefix64 Length(16bits) (in octets),56/8=7
			b64 = fixlength[0];
			b65 = fixlength[1];
			// Prefix64+Suffix=96(bits)? 96/8=12bytes
			// Prefix64 (Variable)
			b66 = prefixsuffix[0];
			b67 = prefixsuffix[1];
			b68 = prefixsuffix[2];
			b69 = prefixsuffix[3];
			b70 = prefixsuffix[4];
			b71 = prefixsuffix[5];
			b72 = prefixsuffix[6];
			// Suffix (Variable)
			b73 = prefixsuffix[7];
			b74 = prefixsuffix[8];
			b75 = prefixsuffix[9];
			b76 = prefixsuffix[10];
			b77 = prefixsuffix[11];
			String[] sarray = null;
			String s = returnfixstring(fixlength, prefixsuffix);
			sarray = mysql.search(s);

			// IPv4 Prefix Count(8bits)
			b78 = (byte) sarray.length;
			b79 = new byte[sarray.length];
			b80 = new byte[sarray.length];
			b81 = new byte[sarray.length];
			b82 = new byte[sarray.length];
			b83 = new byte[sarray.length];
			for (int i = 0; i < sarray.length; i++) {
				System.out.println(sarray[i]);
				// IPv4 Prefix Length(8bits)
				int prefixlength = Integer.valueOf(sarray[i].substring(59,
						sarray[i].length()));
				b79[i] = (byte) prefixlength;
				// IPv4 Address (32 bits)
				int i1 = Integer.valueOf(sarray[i].substring(43, 46));
				int i2 = Integer.valueOf(sarray[i].substring(47, 50));
				int i3 = Integer.valueOf(sarray[i].substring(51, 54));
				int i4 = Integer.valueOf(sarray[i].substring(55, 58));

				System.out.println(i1 + " " + i2 + " " + i3 + " " + i4 + " "
						+ prefixlength);

				b80[i] = (byte) i1;
				b81[i] = (byte) i2;
				b82[i] = (byte) i3;
				b83[i] = (byte) i4;
			}
			// gou zao wan zheng bao ,fa song shu ju
			byte[] bytesToSendqm = { b0, b1, b2, b3, b4, b5, b6, b7, b8, b9,
					b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21,
					b22, b23, b24, b25, b26, b27, b28, b29, b30, b31, b32, b33,
					b34, b35, b36, b37, b38, b39, b40, b41, b42, b43, b44, b45,
					b46, b47, b48, b49, b50, b51, b52, b53, b54, b55, b56, b57,
					b58, b59, b60, b61, b62, b63, b64, b65, b66, b67, b68, b69,
					b70, b71, b72, b73, b74, b75, b76, b77, b78 };
			byte[] bytesToSendfix = new byte[5 * b79.length];
			for (int j = 0; j < b79.length; j++) {
				bytesToSendfix[5 * j] = b79[j];
				bytesToSendfix[5 * j + 1] = b80[j];
				bytesToSendfix[5 * j + 2] = b81[j];
				bytesToSendfix[5 * j + 3] = b82[j];
				bytesToSendfix[5 * j + 4] = b83[j];
			}
			byte[] bytesToSend = new byte[79 + 5 * b79.length];
			System.arraycopy(bytesToSendqm, 0, bytesToSend, 0, 79);
			System.arraycopy(bytesToSendfix, 0, bytesToSend, 79, 5 * b79.length);
			// ipv6 ke yi zhi ding duan kou wang hui fa, ipv4 bu xing
			// response(mCurrentAddr, mCurrentPort, bytesToSend);
			try {
				response(mCurrentAddr, 5350, bytesToSend);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bytesToSend = null;
		}
	}

	public static void jiequprefixsuffix() {

		byte[] b1 = { tempArray[64], tempArray[65] };
		System.arraycopy(b1, 0, fixlength, 0, 2);

		byte[] b2 = { tempArray[66], tempArray[67], tempArray[68],
				tempArray[69], tempArray[70], tempArray[71], tempArray[72],
				tempArray[73], tempArray[74], tempArray[75], tempArray[76],
				tempArray[77] };
		System.arraycopy(b2, 0, prefixsuffix, 0, 12);
	}

	// qiu qian zhui ge shu
	public static String[] qiuchujigeprefix(String[] sarray) {
		String[] prefixgeshu = new String[sarray.length];
		for (int i = 0; i < sarray.length; i++) {
			prefixgeshu[i] = "-1";
		}
		int index = 0;
		boolean flag;
		for (int i = 0; i < sarray.length; i++) {
			flag = true;
			// pan duan shi fou you xiang tong de
			for (int j = 0; j < sarray.length; j++) {
				if (prefixgeshu[j].equals(sarray[i].substring(0,
						sarray[i].indexOf(",")))) {
					flag = false;
				}
			}
			if (flag) {
				prefixgeshu[index] = sarray[i].substring(0,
						sarray[i].indexOf(","));
				index++;
			}
		}

		String[] qianzhuigeshu = new String[index];
		System.out.println("qian zhui ge shu: " + index);
		for (int i = 0; i < index; i++) {
			qianzhuigeshu[i] = prefixgeshu[i];
			System.out.println("qian zhui wei: " + qianzhuigeshu[i]);
		}
		return qianzhuigeshu;
	}

	// qian zhui jie qu
	public static byte[] jiequqianzhui(String qianzhui) {
		// yi gong 14 ge , qian liang ge shi qian zhui chang du,
		// hou 12 ge shi prefix+suffix,qian liang ge shi qian zhui chang du
		byte[] qianzhuis = new byte[14];
		// qian zhui chang du
		qianzhuis[0] = (byte) ((Integer.valueOf(qianzhui.substring(
				qianzhui.indexOf("/") + 1, qianzhui.length())) / 8) / 16);
		qianzhuis[1] = (byte) ((Integer.valueOf(qianzhui.substring(
				qianzhui.indexOf("/") + 1, qianzhui.length())) / 8) % 16);
		System.out.println("qian zhui chang du: 0x" + qianzhuis[0]
				+ qianzhuis[1]);

		// prefix+suffix
		// xue xi 16 jin zhi zi fu chuan zhuan wei 16 jin zhi shu
		// new java.math.BigInteger("2c", 16).intValue();
		// qianzhuis[2] = (byte) new java.math.BigInteger(
		// qianzhui.substring(0, 2), 16).intValue();
		// qianzhuis[3] = (byte) new java.math.BigInteger(
		// qianzhui.substring(2, 4), 16).intValue();
		// qianzhuis[4] = (byte) new java.math.BigInteger(
		// qianzhui.substring(5, 7), 16).intValue();
		// qianzhuis[5] = (byte) new java.math.BigInteger(
		// qianzhui.substring(7, 9), 16).intValue();
		// qianzhuis[6] = (byte) new java.math.BigInteger(qianzhui.substring(10,
		// 12), 16).intValue();
		// qianzhuis[7] = (byte) new java.math.BigInteger(qianzhui.substring(12,
		// 14), 16).intValue();
		// qianzhuis[8] = (byte) new java.math.BigInteger(qianzhui.substring(15,
		// 17), 16).intValue();
		// qianzhuis[9] = (byte) new java.math.BigInteger(qianzhui.substring(17,
		// 19), 16).intValue();
		// qianzhuis[10] = (byte) new
		// java.math.BigInteger(qianzhui.substring(20,
		// 22), 16).intValue();
		// qianzhuis[11] = (byte) new
		// java.math.BigInteger(qianzhui.substring(22,
		// 24), 16).intValue();
		// qianzhuis[12] = (byte) new
		// java.math.BigInteger(qianzhui.substring(25,
		// 27), 16).intValue();
		// qianzhuis[13] = (byte) new
		// java.math.BigInteger(qianzhui.substring(27,
		// 29), 16).intValue();

		// prefix+suffix
		InetAddress address = InetAddressAndByteAndString
				.StringToInetAddress(qianzhui.substring(0,
						qianzhui.indexOf("/")));
		byte[] bt = InetAddressAndByteAndString.InetAddressToByte(address);
		for (int i = 2; i < 14; i++) {
			qianzhuis[i] = bt[i - 2];
			// System.out.print(qianzhuis[i] + "  ");
		}
		// System.out.println();
		return qianzhuis;
	}
}
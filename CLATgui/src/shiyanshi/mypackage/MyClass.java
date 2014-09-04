package shiyanshi.mypackage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyClass {

	public UDPEchoClientTimeout udpET;
	// 这个局域网ip得是本机的无线网络的ip，而不是本机本地连接的有线ip
	// public String ServerIP = "172.27.35.1";
	// 远程服务器的ipv4地址
	// public String ServerIP = "210.25.132.162";
	// 远程服务器的ipv6地址
	// public String ServerIP = "2001:250:f004:f001:d843:621e:dbeb:67b2";
	public String ServerIP = "2001:250:f004:f001:f1d0:3fad:4a43:e0a6";
	// public String ServerIP = "2001:da8:215:819:bc38:71d:4896:2695";
	public int ServerPort = 5351;
	//
	// 写入文件用的，以及文件中项目行数
	String xrstring = "0" + "\n";;
	int hangshu = 0;
	//
	public static String[] ipv6fixstring = new String[1000];
	public static String[][] fixstring = new String[1000][];
	public static boolean[] isfixstringkeyong = new boolean[1000];
	//
	public static byte[] bytes = new byte[78];
	//
	public ServerRecvThreadbz recvThread;
	public Context context;

	// 写文件类
	FileService service;
	// 设置一个变量，判断是否需要覆盖内容，因为有多个包同时返回，这几个包不能相互覆盖，但是每次查询的过程可以进行覆盖
	boolean isfugai = false;

	// 构造函数
	public MyClass(Context context) {
		this.context = context;
	}

	//
	public void go() {
		initview();
		//
		recvThread = new ServerRecvThreadbz();
		recvThread.start();
	}

	public void recvThreadstop() {
		recvThread.StopThread();
	}

	public void initview() {
		for (int i = 0; i < 1000; i++) {
			// 学习这个，可以只初始化高维的，本来是二维的
			fixstring[i] = null;
			isfixstringkeyong[i] = true;
		}
		// 初始化写文件类
		service = new FileService(context);
	}

	public void chaxun() {
		byte[] bytesToSend = gouzaobytechaxun();
		UDPEchoClientTimeout udpET = new UDPEchoClientTimeout(context, handler,
				ServerIP, ServerPort, bytesToSend);
		udpET.start();
		bytesToSend = null;
		System.gc();
	}

	public void chaxunall() {
		byte[] bytesToSend = gouzaobytechaxunall();
		UDPEchoClientTimeout udpET = new UDPEchoClientTimeout(context, handler,
				ServerIP, ServerPort, bytesToSend);
		udpET.start();
		bytesToSend = null;
		System.gc();
		// 进行一次查询，可以对之前的进行覆盖
		isfugai = true;
	}

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			// 判断是否要让xrstring置为空，hangshu置为0
			if (isfugai) {
				// 每次查询之间xrstring重新置为空
				xrstring = "0" + "\n";
				hangshu = 0;
				// 别忘了可以覆盖之后，将isfugai值设置为false，即这之后可以追加内容的写入，等待下一次查询再进行覆盖写入
				isfugai = false;
			}

			// 获取到handler发来的是哪个k，即数组fixstring[k]可用，然后把fixstring[k]显示出来
			// 显示出来后，再把它对应的isfixstringkeyong[k]置为true，即下次又可以给fixstring[k]赋值了
			// 作为第一行的一个整数表示有多少条数据，用于c程序读取时创建数组
			int k = msg.what;
			isfixstringkeyong[k] = true;
			hangshu = hangshu + fixstring[k].length / 5;
			// 写入文件的string变量与显示的string变量不太同，每次都从新开始接收，不许累积上一次的内容
			String lsxrstring = "";
			for (int i = 0; i < fixstring[k].length / 5; i++) {
				lsxrstring = lsxrstring + ipv6fixstring[k] + " ";
				lsxrstring = lsxrstring + fixstring[k][i * 5 + 1] + "."
						+ fixstring[k][i * 5 + 2] + "."
						+ fixstring[k][i * 5 + 3] + "."
						+ fixstring[k][i * 5 + 4] + "\0" + " "
						+ fixstring[k][i * 5] + "\0" + "\n";
			}

			xrstring = xrstring + lsxrstring;
			xrstring = String.valueOf(hangshu)
					+ xrstring.substring(1, xrstring.length());

			// 采用覆盖原文件的内容的写入方法
			try {
				// 删文件的代码放到了MainActivity中的onKeyDown函数中了
				// File file = new
				// File("/data/data/edu.bupt.Clat/files/abc.conf");
				// if (file.exists())
				// file.delete();
				service.save("abc.conf", xrstring);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(context, "写文件失败", Toast.LENGTH_SHORT).show();
			}
		}
	};

	public static byte[] gouzaobytechaxun() {

		gouzaoqianmian();

		// (optional) PCP Options
		// Option Code(8bits)
		bytes[60] = (byte) 129;
		// Reserved(8bits)
		bytes[61] = (byte) 0;
		// Option Length(16bits) (in octets)
		bytes[62] = (byte) 0;
		bytes[63] = (byte) 0x4;
		// Prefix64 Length(16bits) (in octets),56/8=7
		bytes[64] = (byte) 0;
		bytes[65] = (byte) 0x7;
		// Prefix64+Suffix=96(bits)? 96/8=12bytes
		// Prefix64 (Variable)
		bytes[66] = (byte) 0x20;
		bytes[67] = (byte) 0x01;
		bytes[68] = (byte) 0x0d;
		bytes[69] = (byte) 0xb8;
		bytes[70] = (byte) 0x01;
		bytes[71] = (byte) 0x22;
		bytes[72] = (byte) 0x03;
		// Suffix (Variable)
		bytes[73] = (byte) 0x00;
		bytes[74] = (byte) 0x00;
		bytes[75] = (byte) 0x00;
		bytes[76] = (byte) 0x00;
		bytes[77] = (byte) 0x00;

		byte[] bytesToSend = bytes;
		return bytesToSend;
	}

	public static byte[] gouzaobytechaxunall() {

		gouzaoqianmian();

		// (optional) PCP Options
		// Option Code(8bits)
		bytes[60] = (byte) 129;
		// Reserved(8bits)
		bytes[61] = (byte) 0x00;
		// Option Length(16bits) (in octets)
		bytes[62] = (byte) 0x00;
		bytes[63] = (byte) 0x04;
		// ke hu duan guding wei ::/96
		// Prefix64 Length(16bits) (in octets),96/8=12
		bytes[64] = (byte) 0x00;
		bytes[65] = (byte) 0x0c;
		// Prefix64+Suffix=96(bits)? 96/8=12bytes
		// Prefix64 (Variable)
		bytes[66] = (byte) 0x00;
		bytes[67] = (byte) 0x00;
		bytes[68] = (byte) 0x00;
		bytes[69] = (byte) 0x00;
		bytes[70] = (byte) 0x00;
		bytes[71] = (byte) 0x00;
		bytes[72] = (byte) 0x00;
		bytes[73] = (byte) 0x00;
		bytes[74] = (byte) 0x00;
		bytes[75] = (byte) 0x00;
		bytes[76] = (byte) 0x00;
		bytes[77] = (byte) 0x00;
		// Suffix (Variable),zhe li mei you hou zhui

		byte[] bytesToSend = bytes;
		return bytesToSend;
	}

	// byte[] to bits
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

	public static void gouzaoqianmian() {

		// Version(8bits)
		bytes[0] = (byte) 0x2;
		// R and Opcode(8bits)
		String str1 = "0" + "0000001";
		bytes[1] = decodeBinaryString(str1);
		// Reserved(16bits)
		bytes[2] = (byte) 0;
		bytes[3] = (byte) 0;
		// Lifitime(32bits)
		bytes[4] = (byte) 0;
		bytes[5] = (byte) 0;
		bytes[6] = (byte) 0x0e;
		bytes[7] = (byte) 0x10;
		// PCP Client's IP Address (128 bits)
		bytes[8] = (byte) 0;
		bytes[9] = (byte) 0;
		bytes[10] = (byte) 0;
		bytes[11] = (byte) 0;
		bytes[12] = (byte) 0;
		bytes[13] = (byte) 0;
		bytes[14] = (byte) 0;
		bytes[15] = (byte) 0;
		bytes[16] = (byte) 0;
		bytes[17] = (byte) 0;
		bytes[18] = (byte) 0;
		bytes[19] = (byte) 0;
		bytes[20] = (byte) 0;
		bytes[21] = (byte) 0;
		bytes[22] = (byte) 0;
		bytes[23] = (byte) 0x1;
		// (optional) Opcode-specific response data
		// Map(288bits)
		// mapping nonce(96bits),random
		bytes[24] = (byte) new Random().nextInt(256);
		bytes[25] = (byte) new Random().nextInt(256);
		bytes[26] = (byte) new Random().nextInt(256);
		bytes[27] = (byte) new Random().nextInt(256);
		bytes[28] = (byte) new Random().nextInt(256);
		bytes[29] = (byte) new Random().nextInt(256);
		bytes[30] = (byte) new Random().nextInt(256);
		bytes[31] = (byte) new Random().nextInt(256);
		bytes[32] = (byte) new Random().nextInt(256);
		bytes[33] = (byte) new Random().nextInt(256);
		bytes[34] = (byte) new Random().nextInt(256);
		bytes[35] = (byte) new Random().nextInt(256);
		// protocol(8bits)
		bytes[36] = (byte) 0;
		// reserved(24bits)
		bytes[37] = (byte) 0;
		bytes[38] = (byte) 0;
		bytes[39] = (byte) 0;
		// internal port(16bits)
		bytes[40] = (byte) 0;
		bytes[41] = (byte) 0;
		// suggested external port(16bits)
		bytes[42] = (byte) 0;
		bytes[43] = (byte) 0;
		// suggested external IP Address(128bits)
		bytes[44] = (byte) 0;
		bytes[45] = (byte) 0;
		bytes[46] = (byte) 0;
		bytes[47] = (byte) 0;
		bytes[48] = (byte) 0;
		bytes[49] = (byte) 0;
		bytes[50] = (byte) 0;
		bytes[51] = (byte) 0;
		bytes[52] = (byte) 0;
		bytes[53] = (byte) 0;
		bytes[54] = (byte) 0;
		bytes[55] = (byte) 0;
		bytes[56] = (byte) 0;
		bytes[57] = (byte) 0;
		bytes[58] = (byte) 0;
		bytes[59] = (byte) 0;
	}

	int i = 0;

	public void shezhi(DatagramPacket dataPacket) {
		//
		i++;
		Log.d("cishu", String.valueOf(i));

		String mCurrentAddr = null;
		int mCurrentPort = 0;
		String mCurrent = null;
		byte[] tempArray;
		byte[] data;
		int dataLen;
		//
		mCurrentAddr = dataPacket.getAddress().getHostAddress();
		mCurrentPort = dataPacket.getPort();
		mCurrent = mCurrentAddr + "+" + String.valueOf(mCurrentPort);
		System.out.println(mCurrent);

		data = dataPacket.getData();
		dataLen = dataPacket.getLength();
		tempArray = new byte[dataLen];
		System.arraycopy(data, 0, tempArray, 0, dataLen);
		// System.out.println(tempArray[78]);

		// 这里出现了一个非常严重的问题，这里不能给单一的公有变量赋值，因为当包大量到来是，后来的值就会把之前的值覆盖掉了
		// 这里设置2公有数组，一个用来存放数据，一个用来表示另一个数组对应位可不可用
		// 这技巧有点吊
		if (tempArray[0] == 0) {
		} else {

			//
			for (int k = 0; k < 1000; k++) {
				// 判断该数组是否被占用
				if (isfixstringkeyong[k]) {
					// 记住，进来之后，先把这个数组给锁住
					isfixstringkeyong[k] = false;

					// 显示前缀
					// 取出ipv6前缀长度
					byte[] qianzhuichangdu1 = { tempArray[64] };
					ByteArrayInputStream inchangdu1 = new ByteArrayInputStream(
							qianzhuichangdu1);
					int changdu1 = inchangdu1.read();
					byte[] qianzhuichangdu2 = { tempArray[65] };
					ByteArrayInputStream inchangdu2 = new ByteArrayInputStream(
							qianzhuichangdu2);
					int changdu2 = inchangdu2.read();
					int changdu = changdu1 * 16 + changdu2;
					System.out.println(changdu);
					ipv6fixstring[k] = ":0000:0000" + "\0" + " " + changdu * 8
							+ "\0";
					// 取出前缀
					byte[] qianzhui = { tempArray[66], tempArray[67],
							tempArray[68], tempArray[69], tempArray[70],
							tempArray[71], tempArray[72], tempArray[73],
							tempArray[74], tempArray[75], tempArray[76],
							tempArray[77] };
					for (int i = 11; i >= 0; i--) {// 倒过来赋值
						String s = hextohexstring(qianzhui[i]);
						ipv6fixstring[k] = s + ipv6fixstring[k];
						if (i % 2 == 0 && i != 0) {
							ipv6fixstring[k] = ":" + ipv6fixstring[k];
						}
					}
					System.out.println(ipv6fixstring[k]);

					// 显示前缀对应的ipv4地址
					// 取出ipv6前缀对应的ipv4地址个数
					byte[] counts = { tempArray[78] };
					ByteArrayInputStream incout = new ByteArrayInputStream(
							counts);
					int count = incout.read();
					// 学习这个，可以只初始化高维的，本来是二维的
					fixstring[k] = new String[5 * count];
					for (int j = 0; j < count; j++) {
						int[] t = new int[5];
						for (int i = 79 + 5 * j; i < 84 + 5 * j; i++) {
							byte[] a = { tempArray[i] };
							ByteArrayInputStream in = new ByteArrayInputStream(
									a);
							t[i - 79 - 5 * j] = in.read();
							fixstring[k][i - 79] = String.valueOf(t[i - 79 - 5
									* j]);
							// 补齐3位
							// if (fixstring[k][i - 79].length() == 1) {
							// fixstring[k][i - 79] = "00"
							// + fixstring[k][i - 79];
							// } else if (fixstring[k][i - 79].length() == 2) {
							// fixstring[k][i - 79] = "0"
							// + fixstring[k][i - 79];
							// }
						}
					}
					// 注意，这里发送出去的值是k，表示fixstring[k]需要显示，而不再是像以前一样，发送一个固定值
					handler.sendEmptyMessage(k);
					break;
				}
			}
		}
		data = null;
		tempArray = null;
	}

	public class ServerRecvThreadbz extends Thread {
		byte[] buffer;
		byte[] data;
		boolean flag = true;
		DatagramSocket ds = null;
		int j = 0;

		public void run() {
			try {
				// 指定端口5350，pcp的客户端特定端口
				ds = new DatagramSocket(5350);
				while (flag) {
					buffer = new byte[1024];
					DatagramPacket dataPacket = new DatagramPacket(buffer,
							buffer.length);
					ds.receive(dataPacket);
					j++;
					Log.d("cishujjj", String.valueOf(j));
					shezhi(dataPacket);

					buffer = null;
					data = null;
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void StopThread() {
			flag = false;
		}
	}

	// 16进制转换成16进制的字符串
	public static String hextohexstring(byte b) {
		String s = "";
		// b to unsigned int,very important
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
}

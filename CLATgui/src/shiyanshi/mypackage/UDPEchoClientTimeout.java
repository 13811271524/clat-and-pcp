package shiyanshi.mypackage;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.admin.DeviceAdminInfo;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class UDPEchoClientTimeout extends Thread {

	private static final int TIMEOUT = 3000;
	private static final int MAXTRIES = 5;
	public Context mContext;
	public Handler mHandler;
	public String mdestip;
	public int mdestport;
	public byte[] mbytesToSend;
	public byte[] receivebuffer = new byte[1024];

	// 若收到的数据显示设备是开的还是关的，1表示开，0表示关
	int recvopenoff = 0;

	public UDPEchoClientTimeout(Context context, Handler handler,
			String destip, int destport, byte[] bytesToSend) {
		mContext = context;
		mHandler = handler;
		mdestip = destip;
		mdestport = destport;
		mbytesToSend = new byte[bytesToSend.length];
		System.arraycopy(bytesToSend, 0, mbytesToSend, 0, bytesToSend.length);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			InetAddress serverAddress = InetAddress.getByName(mdestip);
			int servPort = mdestport;

			try {
				// 暂时让客户端端口为5350
				DatagramSocket socket = new DatagramSocket();
				socket.setSoTimeout(TIMEOUT);

				DatagramPacket sendPacket = new DatagramPacket(mbytesToSend,
						mbytesToSend.length, serverAddress, servPort);

				DatagramPacket receivePacket = new DatagramPacket(
						receivebuffer, receivebuffer.length);

				int tries = 0;

				boolean receivedResponse = false;
				// do {
				socket.send(sendPacket);
				// 就不再在这里设置接收数据了，在MainActivity里专门启动一个接收线程监听5350端口，服务器那边指定5350端口返回数据，
				// 注意这种方法只适用于ipv6环境，因为ipv4环境监听不到5350端口，5350端口只是内网端口，通过外网时还会转换，但是ipv6
				// 环境下能监听本ipv6地址下的5350端口，
				// try {
				// socket.receive(receivePacket);
				// if (!receivePacket.getAddress().equals(serverAddress)) {
				// // throw new IOException("接收到了一个未知来源的包");
				// }
				// receivedResponse = true;
				// } catch (InterruptedIOException e) {
				// tries += 1;
				// System.out.println("Time out," + (MAXTRIES - tries)
				// + " more tries....");
				// }
				//
				// } while ((!receivedResponse) && (tries > MAXTRIES));
				//
				// if (receivedResponse) {
				//
				// // 通过mHandler把设备的状态信息传出去，让外面的Activity作相应的处理
				// // mHandler.sendEmptyMessage(3);
				// MainActivity.instance.shezhi(receivePacket);
				// System.out.println("Received:");
				// } else {
				// // 通过mHandler把设备的状态信息传出去，让外面的Activity作相应的处理
				// mHandler.sendEmptyMessage(1);
				// System.out.println("NO response -- giving up.");
				// }
				socket.close();
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		Runnable runnable_ry2 = new Runnable() {

			public void run() {
				// 如果录音器正在录音，继续计时

				// displayToast("2!");

			}
		};

	}

	public class myRunnable implements Runnable {

		public String mMsg = "无数据哦！变态！";

		public myRunnable(String msg) {
			mMsg = msg;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ShowMsg(mMsg);

		}
	}

	public void ShowMsg(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
	}

}

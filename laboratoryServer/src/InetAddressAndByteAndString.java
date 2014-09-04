import java.net.InetAddress;
import java.net.UnknownHostException;

//
public class InetAddressAndByteAndString {
	// String zhuan cheng InetAddress
	public static InetAddress StringToInetAddress(String ipaddr) {
		InetAddress address = null;
		try {
			address = InetAddress.getByName(ipaddr);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
	}

	// InetAddress zhuan cheng String
	public static String InetAddressToString(InetAddress address) {
		String ipaddr = address.toString();
		return ipaddr;
	}

	// Byte[] zhuan cheng InetAddress
	public static InetAddress ByteToInetAddress(byte[] ipAddress) {
		InetAddress address = null;
		try {
			address = InetAddress.getByAddress(ipAddress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
	}

	// InetAddress zhuan cheng Byte
	public static byte[] InetAddressToByte(InetAddress address) {
		byte[] ipAddress = address.getAddress();
		return ipAddress;
	}
}

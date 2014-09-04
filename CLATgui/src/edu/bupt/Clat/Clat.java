package edu.bupt.Clat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

//import android.app.Activity;
import android.content.Context;
import android.content.Intent;
//import android.net.wifi.WifiManager;
//import android.net.wifi.WifiInfo;
//import android.os.Bundle;
import android.util.Log;

public class Clat {
	private static String ClatInterface = null;
	private static boolean hasClatdState = false;
	private static File ClatState = new File(InstallBinary.DATA_DIR,
			"clat.state");
	// public String WiFiMacAddr = GetMacIP.getLocalMacAddress();
	// Bundle mbundle = this.getIntent().getExtras();
	// String[] ClatSubfix = mbundle.getStringArray(GetMacIP.v6ID);
	public static String OriginDefaultRoute = null;
	private static String running = null;

	public static String getClatInterface() {
		InitFromDisk();
		return (ClatInterface == null) ? "" : ClatInterface;
	}

	public static boolean ClatRunning() {
		InitFromDisk();

		try {
			running = RunAsRoot.execCommand("ip addr | grep clat");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Log.d("running", "upy " + running + "012");

		if (running == null || running.equals("")) {
			Log.d("running", "upyAA");
			return false;
		}

		Log.d("running", "upyK " + running + " kkk " + ClatInterface);
		return ClatInterface != null;
		// return true;
	}

	private static void WriteClatState(String interfaceName) {
		try {
			FileOutputStream ClatState_out = new FileOutputStream(
					ClatState.getPath(), false);
			String state = interfaceName + "\n";
			ClatState_out.write(state.getBytes());
			ClatState_out.close();
		} catch (Exception e) {
			Log.e("Clatd/writestate", e.toString());
		}
	}

	private static void ReadClatState() {
		if (!ClatState.exists()) {
			return;
		}
		try {
			Scanner ClatState_in = new Scanner(ClatState);
			if (ClatState_in.hasNextLine()) {
				ClatInterface = ClatState_in.nextLine();
			}
			hasClatdState = true;
			ClatState_in.close();
		} catch (Exception e) {
			Log.e("Tethering/readstate", e.toString());
		}
	}

	public static void InitFromDisk() {
		if (!hasClatdState) {
			ReadClatState();
		}
	}

	public static void startClat(Context context, String interfaceName)
			throws IOException {
		InitFromDisk();
		// if(ClatInterface != null) {
		if (ClatRunning()) {
			Log.e("startClat", "Clat was already started");
			return;
		}

		ClatInterface = interfaceName;
		hasClatdState = true;
		OriginDefaultRoute = null;

		WriteClatState(interfaceName);

		// MacAddr = new String(static WiFiMacAddr[0]);
		// v6ID = MacToIPv6(MacAddr);

		Log.d("startClat", "Clat is starting on " + interfaceName);

		StringBuffer Script = new StringBuffer();
		File clat_conf = new File("/data/misc/clatd.conf");

		Script.append("#!/system/bin/sh\n");
		Script.append("echo `date` starting clatd_launch >>/data/misc/clatd.log\n");

		if (!clat_conf.exists()) {
			Script.append("cat " + InstallBinary.DATA_DIR
					+ "clatd.conf >/data/misc/clatd.conf\n");
			Script.append("echo ipv6_host_id " + MainActivity.ClatSubfix
					+ " >>/data/misc/clatd.conf\n");
			Script.append("chmod 777 /data/misc/clatd.conf\n");
		}else{
			Script.append("cat " + InstallBinary.DATA_DIR
					+ "clatd.conf >/data/misc/clatd.conf\n");
			Script.append("echo ipv6_host_id " + MainActivity.ClatSubfix
					+ " >>/data/misc/clatd.conf\n");
			Script.append("chmod 777 /data/misc/clatd.conf\n");
		}

		/*
		 * OriginDefaultRoute =
		 * RunAsRoot.execCommand("ip route |grep default |grep "+interfaceName);
		 * if(OriginDefaultRoute != null) {
		 * RunAsRoot.execCommand("ip route del "+OriginDefaultRoute); }
		 * Log.d("clatStart","upy0 "+OriginDefaultRoute);
		 */

		// Script.append("cat "+InstallBinary.DATA_DIR+"clatd.conf >/system/etc/clatd.conf\n");
		// Script.append("echo "+ClatSubfix+" >> /data/misc/clatd.conf\n");
		// Script.append("chmod 644 /system/etc/clatd.conf\n");
		// Script.append(InstallBinary.BIN_DIR+"clatd -i "+interfaceName+" -c /data/misc/clatd.conf >/dev/null 2>&1 &\n");
		Script.append(InstallBinary.BIN_DIR + "clatd -i " + interfaceName
				+ " >/dev/null 2>&1 &\n");
		Log.d("clatStart", "upy0.1 " + interfaceName);
		Script.append("CLATPID=$!\n");
		Script.append("echo $CLATPID >" + InstallBinary.DATA_DIR
				+ "clatd.pid\n");
		Script.append("echo started clat, pid = $CLATPID\n");
		Script.append("echo `date` ending clatd_launch, pid = $CLATPID >>/data/misc/clatd.log\n");

		Script.append("echo 1 > /proc/sys/net/ipv6/conf/all/forwarding\n");
		Script.append("echo 1 > /proc/sys/net/ipv6/conf/clat/forwarding\n");
		Script.append("echo 1 > /proc/sys/net/ipv6/conf/clat4/forwarding\n");
		Script.append("echo 1 > /proc/sys/net/ipv6/conf/all/proxy_ndp\n");
		Script.append("echo 1 > /proc/sys/net/ipv6/conf/clat/proxy_ndp\n");
		Script.append("echo 1 > /proc/sys/net/ipv6/conf/clat4/proxy_ndp\n");

		Log.d("clatStart", "upy0.2 " + MainActivity.ClatIPv6Addr);
		Script.append("ip -6 neigh add proxy " + MainActivity.ClatIPv6Addr
				+ " dev " + RunAsRoot.execCommand("getprop wifi.interface")
				+ "\n");

		Script.append("setprop net.dns1 8.8.8.8\n");
		// Log.d("clatStart","upyA");
		Script.append("setprop net.dns2 114.114.115.115\n");
		Script.append("setprop net.wlan0.dns1 8.8.8.8\n");
		// Log.d("clatStart","upyB");
		Script.append("setprop net.wlan0.dns2 114.114.115.115\n");
		Script.append("setprop dhcp.wlan0.dns4 8.8.8.8\n");
		// Log.d("clatStart","upyC");
		Script.append("setprop dhcp.wlan0.dns3 114.114.115.115\n");

		Intent startClat = new Intent(context, RunAsRoot.class);
		startClat.putExtra(RunAsRoot.EXTRA_STAGE_NAME, "start_clat");
		startClat.putExtra(RunAsRoot.EXTRA_SCRIPT_CONTENTS, Script.toString());
		context.startService(startClat);
	}

	public static void stopClatIfStarted(Context context) {
		InitFromDisk();
		ClatState.delete();
		if (ClatInterface != null) {
			stopClat(context);
		}
	}

	public static void stopClat(Context context) {
		ClatInterface = null;

		Log.d("stopClat", "Clat is stopping");

		StringBuffer Script = new StringBuffer();
		Script.append("#!/system/bin/sh\n");
		Script.append("echo `date` starting clatd_kill >>/data/misc/clatd.log\n");
		Script.append("CLATPID=`cat " + InstallBinary.DATA_DIR + "clatd.pid`\n");
		Script.append("echo killing pid $CLATPID\n");
		Script.append("kill $CLATPID >>/data/misc/clatd.log 2>&1\n");
		Script.append("rm " + InstallBinary.DATA_DIR + "clatd.pid\n");
		Script.append("echo `date` ending clatd_kill, pid=$CLATPID >>/data/misc/clatd.log\n");

		Intent stopClat = new Intent(context, RunAsRoot.class);
		stopClat.putExtra(RunAsRoot.EXTRA_STAGE_NAME, "stop_clat");
		stopClat.putExtra(RunAsRoot.EXTRA_SCRIPT_CONTENTS, Script.toString());

		/*
		 * Log.d("start","upy1 "+OriginDefaultRoute); if(OriginDefaultRoute !=
		 * null || OriginDefaultRoute.equals("")) { try {
		 * RunAsRoot.execCommand("ip route add "+OriginDefaultRoute); } catch
		 * (IOException e) {
		 * 
		 * e.printStackTrace(); } }
		 */

		context.startService(stopClat);
	}

	/*
	 * public String getLocalMacAddress() {
	 * 
	 * WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	 * WifiInfo info = wifi.getConnectionInfo();
	 * 
	 * return info.getMacAddress();
	 * 
	 * }
	 */

}
package com.zcj.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Formatter;
import java.util.Locale;

public class UtilPc {

	/**
	 * 获取电脑的MAC地址，如：78-E3-B5-A5-F5-B3
	 * 
	 * @return 如果获取失败，则返回NULL
	 */
	@SuppressWarnings("resource")
	public static String getMac() {
		try {
			InetAddress address = InetAddress.getLocalHost();
			NetworkInterface ni = NetworkInterface.getByInetAddress(address);
			byte[] mac = ni.getHardwareAddress();
			String sMAC = "";
			Formatter formatter = new Formatter();
			for (int i = 0; i < mac.length; i++) {
				sMAC = formatter.format(Locale.getDefault(), "%02X%s", mac[i], (i < mac.length - 1) ? "-" : "").toString();
			}
			return sMAC;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(getMac());
	}
}

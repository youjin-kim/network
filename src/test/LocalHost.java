package test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class LocalHost {

	public static void main(String[] args) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostName = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			
			System.out.println(hostName);
			System.out.println(hostAddress);
			
			byte[] ipAddresses = inetAddress.getAddress();
			for(byte ipAddress : ipAddresses) {
				System.out.print((int)ipAddress & 0x000000ff);
				System.out.print(".");
				
			}
			//System.out.println(Arrays.toString(ipAddresses));
			
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}

	}

}

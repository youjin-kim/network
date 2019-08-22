package utill;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String address = null;
		
		try {
			while(true) {
				System.out.print("> ");
				address = scanner.nextLine();
				
				if (address.equals("exit")) {
					break;
				}

				InetAddress[] inetAddresses = InetAddress.getAllByName(address);
	
				for (InetAddress inetAddress : inetAddresses) {
					System.out.println(address + ": " + inetAddress.getHostAddress());
				}
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
				
	}

}

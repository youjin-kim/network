package echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static String SERVER_IP = "192.168.1.10";
	private static int SERVER_PORT = 8500;

	public static void main(String[] args) { //입력 무한 루프, exit 종료
		Socket socket = null;
		
		try {
			//1. 소켓생성
			socket = new Socket();
			
			//2. 서버연결
			InetSocketAddress inetSocktAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(inetSocktAddress);
			
			System.out.println("[ECHOClient] connected");
			
			//3. IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			Scanner scanner = new Scanner(System.in);
			String str = null;
			
			//4. 쓰기
			while(true) {
				System.out.print(">> ");
				str = scanner.nextLine();
				
				if (str.equals("exit")) {
					break;
				}
				
				os.write(str.getBytes("UTF-8"));
				
				//5. 읽기
				byte[] buffer = new byte[256];
				int readByteCount = is.read(buffer); // Blocking
				
				if (readByteCount == -1) {
					System.out.println("[ECHOServer] closed by client");
					return;
				}
				
				str = new String(buffer, 0, readByteCount, "UTF-8");
				System.out.println("<< " + str);
			
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}

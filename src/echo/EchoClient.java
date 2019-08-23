package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static String SERVER_IP = "192.168.1.10";
	private static int SERVER_PORT = 8500;

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = null;
		
		try {
			//1. Scanner 생성
			scanner = new Scanner(System.in);
			
			//2. 소켓생성
			socket = new Socket();
			
			//3. 서버연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			
			log("connected");
			
			//4. IOStream 생성하기
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			
			while(true) {
				//5. 키보드 입력
				System.out.print(">> ");
				String str = scanner.nextLine();
				if (str.equals("exit")) {
					break;
				}
				
				//6. 데이터 쓰기(전송)
				pw.println(str);

				//7. 데이터 읽기(수신)
				String data = br.readLine();
				if (data == null) {
					log("closed by client");
					break;
				}
				
				//8. 콘솔 출력
				System.out.println("<< " + data);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(scanner != null) {
					scanner.close();
				}
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	private static void log(String log) {
		System.out.println("[Echo Client] " + log);
	}

}


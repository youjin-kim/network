package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	private static String SERVER_IP = "192.168.56.1";
	private static int SERVER_PORT = 8500;

	public static void main(String[] args) {
		Scanner scanner = null;
		Socket socket = null;

		try {
			// 1. 키보드 연결
			scanner = new Scanner(System.in);

			// 2. socket 생성
			socket = new Socket();

			// 3. 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			log("connected");

			// 4. reader/writer 생성
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			// 5. join 프로토콜
			System.out.print("닉네임>>");
			String nickname = scanner.nextLine();
			printWriter.println("join:" + nickname);
			printWriter.flush();

			// 6. ChatClientThread 시작
			ChatClientThread cct = new ChatClientThread(socket);
			cct.start();

			// 7. 키보드 입력 처리
			while (true) {
				String input = scanner.nextLine();

				if ("quit".equals(input)) {
					// 8. quit 프로토콜 처리
					printWriter.println("quit:");
					break;
				} else {
					// 9. 메시지 처리
					printWriter.println("message:" + input);
				}

			}

		} catch (IOException ex) {
			log("error:" + ex);
		} finally {
			try {
				if (scanner != null) {
					scanner.close();
				}
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void log(String log) {
		System.out.println("[Chat Client#" + Thread.currentThread().getId() + "] " + log);
	}

}

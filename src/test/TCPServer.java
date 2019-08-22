package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {
private static final int PORT = 4000;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		try {
			//1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			//2. Binding:
			//   Socket에 SocketAddress(IPAddress + Port)
			//   바인딩한다.
			InetAddress inetAddress = InetAddress.getLocalHost();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, PORT);
			serverSocket.bind(inetSocketAddress);
			System.out.println("[TCPServer] binding " + inetAddress.getHostAddress() + ":" + PORT);
			//클라이언트가 서버에 접속할 주소
			
			//3. accept:
			//   클라이언트로 부터 연결요청(Connect)을 기다린다.
			Socket socket = serverSocket.accept(); // Blocking  //통신용 socket
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress(); //연결된 시스템에 대한 주소를 반환한다.
			
			String remoteHostAdress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetRemoteSocketAddress.getPort();
			
			System.out.println("[TCPServer] connected from client[" + remoteHostAdress + ":" + remoteHostPort +
			"]");
			
			try {
				//4. IOStream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				while(true) {
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer); //Blocking
					if(readByteCount == -1) {
						//정상종료: remote socket이 close()
						//		메소드를 통해서 정상적으로 소켓을 닫은 경우
						System.out.println("[TCPServer] closed by client");
						break;
					}
					
					String data = new String(buffer, 0, readByteCount, "UTF-8");
					System.out.print("[TCPServer] received: " + data);
				}
			} catch(SocketException e) {
				System.out.println("[TCPServer] abnormal closed by client");	
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//. 자원정리
			try {
				if(serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}

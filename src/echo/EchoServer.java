package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	private static final int PORT = 8500;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket();
			
			InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), PORT);
			serverSocket.bind(inetSocketAddress);
			log("binding " + InetAddress.getLocalHost().getHostAddress() + ":" + PORT);
			
			Socket socket = serverSocket.accept();
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetRemoteSocketAddress.getPort();
			
			log("connected from client[" + remoteHostAddress + ":" + remoteHostPort + "]");
			
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true); //쓰는 즉시 보내라
				
				while(true) {
					//데이터 읽기
					String data = br.readLine();
					if(data == null) {
						log("closed by client");
						break;
					}
					
					log("received: " + data);
					
					//데이터 쓰기(송신)
					pw.println(data);
				}
				
			} catch(SocketException e) {
				System.out.println("[ECHOServer] abnormal closed by client");
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
			try {
				if(serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static void log(String log) {
		System.out.println("[Echo Server] " + log);
		
	}

}

package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Server {
	private ServerSocket serverSocket;
	public Server() {}
	public void initialize() {
		try {
			this.serverSocket = new ServerSocket(Constant.PORTNUMBER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() { //1초 100
		try {
//			this.serverSocket.setSoTimeout(10000);
			boolean brunning = true;
			while(brunning) {
				Socket clientSocket = this.serverSocket.accept();
				// create Thread
				Skeleton skeleton = new Skeleton(clientSocket);
				skeleton.start();
//				this.serverSocket.setSoTimeout(100000);
			} 
		} catch(SocketTimeoutException e) {
			System.out.println("서버와의 접속이 끊겼습니다.");
			finalize();
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void finalize() {
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

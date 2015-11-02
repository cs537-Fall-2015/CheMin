package generic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RoverServerSocket {
	private ServerSocket serverSocket;
	private Socket socket;
	
	private int port;

	public RoverServerSocket(int port) throws IOException{
		setPort(port);
		serverSocket = getServerSocket();
	}
	public void closeAll() throws IOException{
		if (serverSocket != null)
			serverSocket.close();
		if(socket != null)
			socket.close();
	}
	
	public void closeSocket() throws IOException{
		if(socket != null)
			socket.close();
	}
	
	public ServerSocket getServerSocket() throws IOException {
		if(serverSocket == null)
			serverSocket = new ServerSocket(getPort());
		return serverSocket;
	}
	public void openSocket() throws IOException{
		setSocket(serverSocket.accept());
	}
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}

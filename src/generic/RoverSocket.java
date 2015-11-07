package generic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class RoverSocket {
	
	private int port;
	private InetAddress host;
	private Socket socket;
	
	public RoverSocket(int port, InetAddress host) throws UnknownHostException{
		setPort(port);
		setHost(host);
	}
	public int getPort() {
		return this.port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public Socket getSocket() throws IOException {
		if(socket == null)
			setSocket();
		return socket;
	}
	public Socket getNewSocket() throws IOException {
		setSocket();
		return socket;
	}
	private void setSocket() throws IOException {
		if(this.socket != null)
			socket.close();
		if(host != null)			
			this.socket = new Socket(host, port);
	}
	public InetAddress getHost() throws UnknownHostException {
		if(this.host == null)
			setHost(null);
		return host;
	}
	public void setHost(InetAddress host) throws UnknownHostException {
		if(host == null)
			this.host = InetAddress.getLocalHost();
		else
			this.host = host;
	}
	
	public void closeAll() throws IOException{
		if(this.socket !=null)
			socket.close();
	}
}

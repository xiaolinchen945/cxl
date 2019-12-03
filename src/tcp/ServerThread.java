package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerThread implements Runnable{
	private Socket socket;
	private BufferedReader br;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String data = null;
		try {
			//读取客户端发送的数据
			while((data = readClient()) != null) {
				//向所有用户发送数据
				for(Socket s:Server.list) {
					PrintStream ps = new PrintStream(s.getOutputStream());
					ps.println("------"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
							  +"------\n"+data+"\n----------"+s.getInetAddress().getHostAddress()
							  +"----------");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//读取客户端数据
	public String readClient() {
		String data = null;
		try {
			while((data = br.readLine()) != null) {
				return data;
			}
		} catch (IOException e) {
			Server.list.remove(socket);
			System.out.println("当前在线人数:" + Server.list.size());
		}
		return null;
	}
}

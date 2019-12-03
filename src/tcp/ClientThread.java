package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread implements Runnable{
	@SuppressWarnings("unused")
	private Socket socket;
	//BufferedReader 用来接受服务端发送来的数据
	private BufferedReader br;
	//构造器
	public ClientThread(Socket socket){
		this.socket = socket;
		try {
			//将socket的输入流转化为BufferedReader
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String data = null;
		try {
			//循环读取并打印服务端发送的数据
			while((data = br.readLine()) != null) {
				System.out.println(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

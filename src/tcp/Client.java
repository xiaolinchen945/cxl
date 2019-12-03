package tcp;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
	1.Client创建Socket
		把socket对象传给ClientThread
		循环打印要说的话
	2.ClientThread接收打印服务端发来的信息
		循环接受服务器的信息
		打印到控制台
	3.Server创建ServerSocket
		创建线程池
		循环等待客户端连接
		把socket对象传给ServerThread
	4.ServerThread接收客户端发送的信息，并把信息发送给所有客户端
		接收客户端的信息
		循环发送给所有客户端
 */

public class Client {

	public static void main(String[] args) throws IOException {
		String name = null;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		//创建Socket
		Socket socket = new Socket("127.0.0.1",60001);
		//创建线程
		new Thread(new ClientThread(socket)).start();
		//打印流
		PrintStream ps = new PrintStream(socket.getOutputStream());
		if(name == null) {
			System.out.println("请输入你的名字：");
			name = sc.next();
			ps.println(name);
		}
		
		String data = null;
		//发送到服务端
		while((data = sc.nextLine()) != null) {
			ps.println(name + "说：" + data);
		}
	}

}

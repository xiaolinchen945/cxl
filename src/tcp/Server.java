package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
	//存储socket对象
	public static List<Socket> list = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		//BufferedReader
		BufferedReader br = null;
		//创建ServerSocket
		try(ServerSocket ss = new ServerSocket(60001);){
			//创建一个线程池，初始为5
			ExecutorService service = Executors.newFixedThreadPool(5);
			
			while(true) {
				Socket s = ss.accept();
				//添加进list集合
				list.add(s);
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				//获得客户端发送的数据
				String data = new ServerThread(s).readClient();
				//判断
				if(data != null) {
					//遍历
					for(Socket socket:list) {
						//打印输出流
						PrintStream ps = new PrintStream(socket.getOutputStream());
						//通知其他人该用户上线了
						ps.println(data + "上线了,当前在线人数:" + Server.list.size());
					}
					System.out.println(data + "上线了,当前在线人数:" + Server.list.size());
				}
				//加入线程池
				service.submit(new ServerThread(s));
			}
		}
	}
}

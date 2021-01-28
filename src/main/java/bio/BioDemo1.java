package bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioDemo1 {
    public static void main(String[] args) throws IOException {
        //线程池
        ExecutorService executorService= Executors.newCachedThreadPool();
        //创建serversocket
        ServerSocket serverSocket=new ServerSocket(6666);
        System.out.println("server started...");
        //监听，等待连接
        while (true){
            //连接成功
            final Socket socket=serverSocket.accept();
            System.out.println("established");
            //创建线程进行通信
            executorService.execute(()->{communicate(socket);});

        }
    }
    public static void communicate(Socket socket){
        try {
            byte[] bytes = new byte[1024];
            //通过socket获取输入流
            InputStream inputStream = socket.getInputStream();

            //读取数据
            while(true){
                int length = inputStream.read(bytes);
                if(length!=-1){
                    System.out.print(new String(bytes,0,length));
                }else break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
                System.out.println("connection shut down");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

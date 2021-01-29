package bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Array;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIODemo1 {
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
            System.out.println("established\n");
            //创建线程进行通信
            executorService.execute(()->{communicate(socket);});
        }
    }
    public static void communicate(Socket socket){
        try {
            byte[] bytes = new byte[10];
            //通过socket获取输入流
            InputStream inputStream = socket.getInputStream();

            System.out.println("Thread ID : "+Thread.currentThread().getId()+" Thread name : "+Thread.currentThread().getName());
            //读取数据
            while(true){
                int length = inputStream.read(bytes);
//                System.out.println(Arrays.toString(bytes));
                if(length!=-1){
                    //                                     -1是因为不让bytes的最后一位被打印
                    //                                     经过打印测试，mac终端按下回车后，
                    //                                     bytes数组会存储ASCII 13和10
                    //                                     也就是回车和换行
                    System.out.println(new String(bytes,0,length-1));
                }else break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
                System.out.println("\nconnection shut down");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

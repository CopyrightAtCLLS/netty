package zerocopy.tradtionalIO;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TraditionalIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("localhost",7001);
        String filename="/Users/chenlvlongshen/Desktop/IMG_6490.jpg";
        InputStream inputStream=new FileInputStream(filename);

        DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());

        byte[] bytes=new byte[4096];
        long length;
        long total=0;

        long startTime=System.currentTimeMillis();

        while ((length=inputStream.read())>=0){
            total+=length;
            dataOutputStream.write(bytes);
        }

        System.out.println("total size of file : "+total+" bytes\ntime cost : "+(System.currentTimeMillis()-startTime)+"ms");

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}

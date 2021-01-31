package zerocopy.zero;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class ZerocopyClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel=SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(7001));

        String filename="/Users/chenlvlongshen/Desktop/IMG_6490.JPG";

        FileChannel channel = new FileInputStream(filename).getChannel();

        long startTime=System.currentTimeMillis();
        //linux下transferTo方法就可以完成传输
        //windows下一次调用transferTo方法只能发送8M文件，需要分段，并且要注意传输时的位置
        //transferTo底层使用零拷贝
        long total=channel.transferTo(0,channel.size(),socketChannel);

        System.out.println("total size of file : "+total+" bytes\ntime cost : "+(System.currentTimeMillis()-startTime)+"ms");
        channel.close();
        socketChannel.close();
    }
}

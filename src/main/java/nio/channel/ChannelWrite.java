package nio.channel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 写入数据到本地文件
 */
public class ChannelWrite {
    public static void main(String[] args) throws IOException {
        String str="channel demo";
        //创建输出流
        FileOutputStream outputStream = new FileOutputStream("origin.txt");
        //通过outputStream获取对应的FileChannel
        FileChannel channel = outputStream.getChannel();
        //创建ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put(str.getBytes());
        buffer.flip();
        channel.write(buffer);

        outputStream.close();
    }
}

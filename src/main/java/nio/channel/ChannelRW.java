package nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 拷贝文件，只使用一个buffer
 */
public class ChannelRW {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("origin.txt");
        FileChannel inputStreamChannel = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream("copy.txt");
        FileChannel outputStreamChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(5);

        while (true) {
            //一定要给buffer复位
            buffer.clear();
            int length = inputStreamChannel.read(buffer);
            if(length==-1) break;
            //一定要flip
            buffer.flip();
            outputStreamChannel.write(buffer);
        }

        inputStream.close();
        outputStream.close();
    }
}

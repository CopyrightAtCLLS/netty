package nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelRead {
    public static void main(String[] args) throws IOException {
        File file=new File("/tmp/nettyChannel");
        FileInputStream inputStream = new FileInputStream(file);

        FileChannel channel = inputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());

        channel.read(buffer);
        System.out.println(new String(buffer.array()));

        inputStream.close();
    }
}

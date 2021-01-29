package nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

/**
 * 拷贝图片
 */
public class ChannelTransPic {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("/Users/clls/Desktop/doge.jpg");
        FileChannel inputStreamChannel = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream("doge.jpg");
        FileChannel outputStreamChannel = outputStream.getChannel();

        outputStreamChannel.transferFrom(inputStreamChannel,0,inputStreamChannel.size());

        inputStream.close();
        outputStream.close();
    }
}

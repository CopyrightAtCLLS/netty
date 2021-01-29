package nio.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer可以让文件直接在内存修改，OS不需要再拷贝
 */
public class MappedByteBufferDemo {
    public static void main(String[] args) throws IOException {

        RandomAccessFile file= new RandomAccessFile("origin.txt", "rw");
        FileChannel channel = file.getChannel();
        /**
         * 使用读写模式
         * 起始位置
         * 映射到内存的大小
         */
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        buffer.put(0,(byte)'H');
        buffer.put(1,(byte)'e');

        file.close();
    }
}

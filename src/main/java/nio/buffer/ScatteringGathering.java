package nio.buffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Mac
 */
public class ScatteringGathering {
    public static void main(String[] args) throws IOException {
        //使用ServerSocketChannel 和 SocketChannel 网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(9999);

        //绑定端口到socket，并启动
        serverSocketChannel.bind(inetSocketAddress);

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int length = 8;//假设一次最多接受8个字节

        while (true) {
            //读
            int byteRead = 0;
            while (byteRead < length) {
                long l = socketChannel.read(byteBuffers);
                byteRead += l;
                System.out.println("ByteRead = "+byteRead);
                Arrays.asList(byteBuffers).stream().map(byteBuffer -> "position = " + byteBuffer.position() +
                        " , limit = " + byteBuffer.limit()).forEach(System.out::println);
            }
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());
            //写
            long byteWritten = 0;
            while (byteWritten < length) {
                long l = socketChannel.write(byteBuffers);
                byteWritten += l;
            }
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());
            System.out.println("ByteRead = " + byteRead + " ByteWritten = " + byteWritten + " message length = " + length);
        }
    }
}

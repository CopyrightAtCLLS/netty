package nio.chattingRoom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class ChattingServer {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private final int PORT = 6667;

    public ChattingServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //注册到selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                int count = selector.select();
                //有事件
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            //设置非阻塞
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + " 进入房间");
                        }
                        if (key.isReadable()) {
                            read(key);
                        }
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void read(SelectionKey key) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int length = channel.read(buffer);
            if (length > 0) {
                String msg = new String(buffer.array(),0,length);
                System.out.println("client : " + msg);
                //向其他客户端转发消息
                forward(msg, channel);
            }
        } catch (IOException e) {
            try {
                //取消注册
                key.cancel();
                channel.close();
                System.out.println(channel.getRemoteAddress() + " 离开房间");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void forward(String msg, SocketChannel self) throws IOException {
        System.out.println("Forwarding...");
        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel&&channel != self) {
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                ((SocketChannel) channel).write(buffer);
            }
        }
//        for (SelectionKey key : selector.keys()) {
//            Channel channel = key.channel();
//            if (channel instanceof SocketChannel&&channel != self) {
//                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
//                ((SocketChannel) channel).write(buffer);
//            }
//        }
    }

    public static void main(String[] args) {
        ChattingServer server = new ChattingServer();
        server.listen();
    }
}

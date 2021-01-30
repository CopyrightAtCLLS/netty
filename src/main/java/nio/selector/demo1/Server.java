package nio.selector.demo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设定非阻塞
        serverSocketChannel.configureBlocking(false);

        //注册serverSocketChannel到selector 关心事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //监听
        while(true){
            //等待1s
            if(selector.select(1000)==0){
                System.out.println("1s , no request");
                continue;
            }

            //如果有请求,获取selectionKey集合
            Set<SelectionKey> selectionKeys=selector.selectedKeys();
            Iterator<SelectionKey> iterator=selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey=iterator.next();
                if(selectionKey.isAcceptable()){//OP_ACCEPT
                    //生成socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //注册socketChannel到selector,关注事件为 OP_READ，同时关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(selectionKey.isReadable()){//OP_READ
                    //通过key得到channel
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    //获取该channel的buffer
                    ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();
                    int length = channel.read(buffer);
                    if(length!=-1)
                    System.out.println("data : "+new String(buffer.array(),0,length));
                }
                //从集合移除key，防止重复操作
                iterator.remove();
            }
        }
    }
}

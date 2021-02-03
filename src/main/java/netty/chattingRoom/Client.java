package netty.chattingRoom;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Scanner;

public class Client {
    private final String host;
    private final int port;
    public Client(String host,int port){
        this.host=host;
        this.port=port;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //向pipiline加入解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            //向pipiline加入编码器
                            pipeline.addLast("encoder", new StringDecoder());
                            //自定义handler
                            pipeline.addLast(null);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            System.out.println("------------"+channel.localAddress()+"------------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg=scanner.nextLine();
                channel.writeAndFlush(msg+"\r\n");
            }
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Client("localhost",7000).run();
    }
}

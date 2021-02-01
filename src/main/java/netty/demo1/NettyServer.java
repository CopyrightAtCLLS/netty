package netty.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建Bossgroup和Workergroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建服务器端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)//设置两个线程组
                .channel(NioServerSocketChannel.class)//设置NioSocketChannel作为通道实现
                .option(ChannelOption.SO_BACKLOG,128)//设置线程队列等待连接的个数
                .childOption(ChannelOption.SO_KEEPALIVE,true)//保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道测试对象
                    //给pipeline设置处理器
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                });//给workerGroup的eventloop对应的管道设置处理器
        System.out.println("Server Online...");
        //绑定端口并同步，生成ChannelFuture对象
        //启动服务器
        ChannelFuture channelFuture=bootstrap.bind(6668).sync();
        //对关闭通道进行监听
        channelFuture.channel().closeFuture().sync();
    }
}

package netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Server {
    public void run() throws Exception{
        //创建Bossgroup和Workergroup
        //boosGroup和workerGroup子线程(NioEventLoop)个数默认是cpu核数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//设置NioSocketChannel作为通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline=socketChannel.pipeline();
                            //因为基于http协议，使用http编码解码器
                            pipeline.addLast(new HttpServerCodec());
                            //以块方式写,添加ChunkedWrite处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            /**
                             * 1、http在传输过程是分段的，HttpObjectAggregator可以把多个段聚合起来
                             * 2、因此，如果浏览器发送数据量很大，会发送多次http请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * 1、对于websocket，是以帧(frame)的形式传递
                             * 2、WebSocketFrame有六个子类
                             * 3、浏览器发送请求时，ws://localhost:7000/xxx   ws代表websocket协议
                             * 4、WebSocketServerProtocolHandler核心功能是将http协议升级为ws协议，保持长连接
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            //自定义handler处理业务逻辑
                            pipeline.addLast(new MyTextWebSocketFrameHandler());
                        }
                    });//给workerGroup的eventloop对应的管道设置处理器
            System.out.println("Server Online...");
            //绑定端口并同步，生成ChannelFuture对象
            //启动服务器
            ChannelFuture channelFuture = bootstrap.bind(9999).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception {
        new Server().run();
    }
}

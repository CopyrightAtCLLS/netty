package netty.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义handler需要继承netty规定好的某个HandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    //数据读取事件

    /**
     * @param ctx 上下文对象，含有pipeline，channel和地址
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server ctx = " + ctx);
        //ByteBuf是netty提供的，不是NIO的ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("Data from client : " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("Client address : " + ctx.channel().remoteAddress());
    }

    //数据读取完成
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //是write + flush，数据写入到缓存并刷新
        //一般要进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端",CharsetUtil.UTF_8));
    }

    //处理异常，一般是关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
    }
}

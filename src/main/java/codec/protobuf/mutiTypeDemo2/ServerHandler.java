package codec.protobuf.mutiTypeDemo2;

import codec.protobuf.simpleDemo1.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义handler需要继承netty规定好的某个HandlerAdapter
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    //数据读取事件

    /**
     * @param ctx 上下文对象，含有pipeline，channel和地址
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读取客户端发送的StudentPOJO.Student
        StudentPOJO.Student student=(StudentPOJO.Student) msg;
        System.out.println("data from client = "+student.getId()+" "+student.getName());
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

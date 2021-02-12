package codec.protobuf.mutiTypeDemo2;

import codec.protobuf.simpleDemo1.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    //通道就绪时，触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发送Student对象到服务器
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(1).setName("clls").build();
        ctx.writeAndFlush(student);
    }
    //当通道有读事件时，触发

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf)msg;
        System.out.println("Data from server : "+buf.toString(CharsetUtil.UTF_8));
        System.out.println("Server address : "+ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

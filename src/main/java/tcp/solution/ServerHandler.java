package tcp.solution;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

public class ServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        int length = messageProtocol.getLen();
        byte[] content=messageProtocol.getContent();

        System.out.println("length = "+length+" content : "+new String(content,CharsetUtil.UTF_8));
        System.out.println("count = "+(++this.count));

        String response=UUID.randomUUID().toString();
        int responseLen=response.getBytes("utf-8").length;
        MessageProtocol message = new MessageProtocol();
        message.setLen(responseLen);
        message.setContent(response.getBytes("utf-8"));
        channelHandlerContext.writeAndFlush(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

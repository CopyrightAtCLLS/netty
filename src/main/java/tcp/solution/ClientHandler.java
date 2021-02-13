package tcp.solution;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol message) throws Exception {
        int length=message.getLen();
        byte[] content = message.getContent();

        System.out.println("length = "+length+" content : "+new String(content,CharsetUtil.UTF_8));
        System.out.println("count = "+(++this.count));
        
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 5; i++) {
            String message = "Hello,Server!";
            byte[] content = message.getBytes(CharsetUtil.UTF_8);
            int length = content.length;

            MessageProtocol messageProtocol=new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

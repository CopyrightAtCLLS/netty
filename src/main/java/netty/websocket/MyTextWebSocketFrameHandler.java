package netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

//TextWebSocketFrame表示一个文本帧
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("服务器收到消息 : " + textWebSocketFrame.text());
        //回复
        channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame("服务器时间 : " + LocalDateTime.now() + textWebSocketFrame.text()));
    }

    //web客户端连接后触发
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id表示唯一的值,longtext表示唯一的值,shottext可能重复
        System.out.println("handler added invoked"+ctx.channel().id().asLongText());
        System.out.println("handler added invoked"+ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //id表示唯一的值,longtext表示唯一的值,shottext可能重复
        System.out.println("handler removed invoked"+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生"+cause.getMessage());
        ctx.close();
    }
}

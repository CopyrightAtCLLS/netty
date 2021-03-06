package netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
 * HttpObject表示客户端和服务器通讯的数据被封装为HttpObject
 */
public class ServerHandler extends SimpleChannelInboundHandler<HttpObject>{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception{
        //判断httpObject是不是Httprequest请求
        if (httpObject instanceof HttpRequest) {
            System.out.println("msg类型 = " + httpObject.getClass());
            System.out.println("客户端地址 = " + channelHandlerContext.channel().remoteAddress());
            //获取请求header
            HttpRequest httpRequest=(HttpRequest)httpObject;
            //获取uri,过滤特定资源
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了favicon.ico，不做响应");
                return;
            }
            //回复信息给浏览器[http]
            ByteBuf content = Unpooled.copiedBuffer("hello，我是服务器", CharsetUtil.UTF_8);
            //构造http响应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //返回
            channelHandlerContext.writeAndFlush(response);
        }
    }
}

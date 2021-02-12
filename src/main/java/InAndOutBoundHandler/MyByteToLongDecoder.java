package InAndOutBoundHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     *  decode会根据接受的数据长度，被调用多次，直到确定没有新的元素被添加到list，或者是Bytebuf没有更多的可读字节为止
     *  如果list out不为空，就会将list的内容传递给下一个channelInboundHandler，该处理器的方法也会被调用多次
     * @param channelHandlerContext 上下文对象
     * @param byteBuf   入站的bytebuf
     * @param list      将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("Decoding...");
        if(byteBuf.readableBytes()>=8){
            list.add(byteBuf.readLong());
        }
    }
}

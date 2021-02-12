package codec.protobuf.mutiTypeDemo2;

import codec.protobuf.simpleDemo1.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    //通道就绪时，触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //随机发送Student或者Worker对象到服务器
        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage=null;

        if(0==random){//发送Student
            myMessage= MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(2).setName("clls").build()).build();
        }else {//发送Worker
            myMessage= MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setAge(20).setName("clls").build()).build();
        }

        ctx.writeAndFlush(myMessage);
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

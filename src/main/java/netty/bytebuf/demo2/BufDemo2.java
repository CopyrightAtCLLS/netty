package netty.bytebuf.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class BufDemo2 {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.copiedBuffer("hello,world!你", Charset.forName("utf-8"));
        if(buffer.hasArray()){
            //获取到ByteBuf的数组
            byte[] array = buffer.array();
            System.out.println(new String(array, Charset.forName("utf-8")));
        }
    }
}

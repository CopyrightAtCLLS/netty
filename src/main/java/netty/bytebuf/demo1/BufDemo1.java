package netty.bytebuf.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class BufDemo1 {
    public static void main(String[] args) {
        //新建ByteBuf,该对象包含一个数组byte[10]
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        //netty的buffer，不需要使用flip()
        //底层维护了readerIndex和writerIndex
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }

        /*for (int i=0;i<buffer.capacity();i++){
            System.out.println(buffer.getByte(i));
        }*/
    }
}

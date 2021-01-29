package nio;

import java.nio.IntBuffer;

public class BufferDemo {
    public static void main(String[] args) {
        //创建容量为5的Buffer
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //存放
        for(int i=0;i<intBuffer.capacity();i++)
            intBuffer.put(i*2);
        //读取
        //将buffer转换，读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining())
            System.out.println(intBuffer.get());
    }
}

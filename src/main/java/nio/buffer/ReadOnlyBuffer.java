package nio.buffer;

import java.nio.ByteBuffer;

public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for(int i=0;i<10;i++)
            buffer.put((byte)i);
        buffer.asReadOnlyBuffer();
        buffer.flip();

        while (buffer.hasRemaining())
            System.out.println(buffer.get());
    }
}

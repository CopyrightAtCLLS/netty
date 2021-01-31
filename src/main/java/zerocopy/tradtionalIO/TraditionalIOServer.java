package zerocopy.tradtionalIO;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TraditionalIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            try {
                byte[] bytes = new byte[4096];
                while (true) {
                    int length = dataInputStream.read(bytes);
                    if (length == -1) break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

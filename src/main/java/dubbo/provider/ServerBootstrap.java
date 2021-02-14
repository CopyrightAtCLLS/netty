package dubbo.provider;

import dubbo.netty.Server;

public class ServerBootstrap {
    public static void main(String[] args) {
        new Server().startServer("localhost",7000);
    }
}

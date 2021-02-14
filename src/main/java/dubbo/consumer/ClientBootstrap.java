package dubbo.consumer;

import dubbo.netty.Client;
import dubbo.publicInterface.HelloService;

import java.util.concurrent.TimeUnit;

public class ClientBootstrap {
    private static final String prefix = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        HelloService helloService = (HelloService) client.getBean(HelloService.class, prefix);
        for(;;) {
            TimeUnit.SECONDS.sleep(5);
            String result = helloService.hello("Hello dubbo");
            System.out.println("result : " + result);
        }
    }
}

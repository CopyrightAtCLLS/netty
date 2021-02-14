package dubbo.provider;

import dubbo.publicInterface.HelloService;

public class HelloServiceImpl implements HelloService {
    private static int count=0;
    @Override
    public String hello(String message) {
        System.out.println("message from client : "+message);
        if(message!=null){
            return "Hello Client, your message ["+message+"] is received! The "+(++this.count)+" time ";
        }else {
            return "Hello Client, your message is received!";
        }
    }
}

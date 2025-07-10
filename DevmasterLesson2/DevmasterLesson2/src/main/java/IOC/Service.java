package IOC;

public class Service {
    public void serve ()
    {
        System.out.println("Service is serving");
    }
}
class Client
{
    private Service service;
    public Client ()
    {
        //client tu tao doi tuong service
        service=new Service();
    }
    public void doSomething ()
    {
        service.serve();
    }
}
//public class NonIoC
//{
//    public static void main(String[] args) {
//        Client client = new Client();
//        client.doSomething();
//    }
//}

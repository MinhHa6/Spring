package DI;

public class Main {
    public static void main(String[] args) {
        // khoi tao doi tuong phu thuoc ben ngoai (IOc)
        MessageService emailService= new EmailService();
        NotificationClient client1 = new NotificationClient(emailService);
        client1.notifyUser("Chao mung ban den vs Ioc bang email");
        //Injrct dependency thong qua setter
        NotificationClient client3 = new NotificationClient(null);
        client3.notifyUser("Thong bao bang Email qua seter");
    }
}

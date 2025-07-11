package DI;

public class NotificationClient {
    private MessageService service;
    public NotificationClient(MessageService service)
    {
        this.service=service;
    }
    public void notifyUser(String message)
    {
        service.sendMessage(message);
    }
}

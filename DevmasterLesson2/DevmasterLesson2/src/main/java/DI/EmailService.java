package DI;

public class EmailService implements MessageService{
    @Override
    public void sendMessage(String message)
    {
        System.out.println("Gui Email voi noi dung :"+message);
    }
}

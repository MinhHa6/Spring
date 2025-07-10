package Lambda;
//lambda ko co tham so
@FunctionalInterface
interface sayhello1
{
    void sayhello ();
}
public class Lambda2 {
    public static void main(String[] args) {
        sayhello1 sayhello = () ->{
            System.out.println("Hello word");
        };
        sayhello.sayhello();
    }
}

package Lambda;
@FunctionalInterface
interface sayHello2
{
    void sayHello (String name);
}
public class Lambda3 {
    public static void main(String[] args) {
        // Lambda su dung 1 tham so
        sayHello2 say1 =(name) -> {
            System.out.println("Hello"+name);
        };
        say1.sayHello("Devmaster");
        // ngan gon
        sayHello2 say2 = name -> {
            System.out.println("Hello"+name);
        };
        say2.sayHello("Ha");
        // ngan gon hon nua
        sayHello2 say3 = name -> System.out.println("Hello"+name);
        say3.sayHello("Dev");
    }
}

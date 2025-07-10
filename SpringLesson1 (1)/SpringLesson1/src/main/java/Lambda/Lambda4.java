package Lambda;
// Lambda vs nhieu tham so
@FunctionalInterface
interface Calculator1 {
    int add (int a,int b);
}
@FunctionalInterface
interface Calculator2 {
    void add (int a,int b);
}
public class Lambda4 {
    public static void main(String[] args) {
        Calculator1 cacl = (int a, int b) -> (a + b);
        System.out.println(cacl.add(3, 4));

        Calculator1 cacl2 = ( a,  b) -> (a + b);
        System.out.println(cacl.add(4, 4));
        Calculator2 calculator2 = (a,b) -> System.out.println(a+b);
        calculator2.add(4,6);
        Calculator2 calculator21=(a,b)->{
            int sum = a+b;
            System.out.println(a+"+"+b+"="+sum);
        };
        calculator21.add(5,6);
    }
}

package pkg_default_method;

public interface Interface1 {
    default void method1()
    {
        System.out.println("Interface,method1");
    }
    public interface Inerface2
    {
        default void method2()
        {
            System.out.println("Inter2,method");
        }
    }
}
class MulInheristance implements Interface1, Interface1.Inerface2 {

    @Override
    public void method1()
    {
        Interface1.super.method1();
    }
    // trung voi phuong thuc method2 trong interface2
    public void method2()
    {
        System.out.println("MulInheristance.Interfacw2");
    }

    public static void main(String[] args) {
        MulInheristance mi = new MulInheristance();
        mi.method1();
        mi.method2();
    }
}
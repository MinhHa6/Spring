package DemoMethodRef;

import java.util.Arrays;

@FunctionalInterface
interface ExecuteFunction {
    public int execure (int a,int b);
}
class MathUtils {
    public MathUtils () {}
    public MathUtils (String str)
    {
        System.out.println("MathUltils: "+str);
    }
    public static int sum (int a, int b)
    {
        return a+b;
    }
    public static int minus (int a, int b)
    {
        return a-b;
    }
    public static int multiply (int a, int b)
    {
        return a*b;
    }
}
public class MetodRef {
    public static void main(String[] args) {
        int a=10;
        int b=20;
        int sum = MathUtils.sum(a,b);
        System.out.println("Tong cua 2 so a va b la: "+sum);
        int minus= MathUtils.minus(a,b);
        System.out.println("Hieu cua 2 so ava b la :"+minus);
        int multipply=doAction(a,b,MathUtils::multiply);
        System.out.println("Tich 2 so a va b la : "+multipply);
        // Tham chieu den 1 instance method cua 1 doi tuong tuy y cu the
        String [] ArrString = {"Java","C++","Php"};
        Arrays.sort(ArrString,String ::compareToIgnoreCase);
        for (String str :ArrString)
        {
            System.out.println(str);
        }
    }
    public static int doAction (int a,int b ,ExecuteFunction func)
    {
        return func.execure(a,b);
    }
}

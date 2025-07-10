package Lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortLambdaExample {
    public static void main(String[] args) {
        List<String>list = Arrays.asList("Spring boot","Php Laravel","C# NEtcore");
        // sap xep su dung bieu thuc lam da
        Collections.sort(list,(String str1,String str2)->str1.compareTo(str2));
        for (String str :list)
        {
            System.out.println(str);
        }
    }
}

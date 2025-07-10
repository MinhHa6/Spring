package Lambda;

import java.util.Arrays;
import java.util.List;

public class Lambda5 {
    public static void main(String[] args) {
        //lambda voi nhieu vong lap
        List<String> list = Arrays.asList("Spring boot","Php Laravel","C# NEtcore");
        // using lambda Expression
        list.forEach(item -> System.out.println(item));
        System.out.println("======================");
        list.forEach(System.out::println);
    }
}

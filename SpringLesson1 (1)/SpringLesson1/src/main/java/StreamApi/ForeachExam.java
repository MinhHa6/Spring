package StreamApi;

import org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration;

import java.util.Arrays;
import java.util.List;

public class ForeachExam {
    public static void main(String[] args) {
        List<String>list = Arrays.asList("PhpLaravel","C# Netcore","Java Spring");
        System.out.println("Su dung bieu thuc lambda:");
        list.forEach(lists-> System.out.println(lists));
        System.out.println("Su dung method referen:");
        list.forEach(System.out::println);
    }
}

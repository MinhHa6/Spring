package com.devmaster.SpringLesson1;

import java.util.*;

public class Lamda {
    public static void main(String[] args) {
        List<String>languges = Arrays.asList("Java","Php","C++","Javacripts");
        Collections.sort(languges, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        for (String languge :languges)
        {
            System.out.println(languge);
        }
        Collections.sort(languges,(String o3,String o4)->{return  o3.compareTo(o4);});
        for (String languge2 :languges)
        {
            System.out.println(languge2);
        }
    }
}

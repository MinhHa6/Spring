package StreamApi;

import java.util.Arrays;
import java.util.List;

public class StreamExam {
        List<Integer>list= Arrays.asList(11,23,43,535,646,6,8);
        // dem cac so chan ko dung stream
        public void withoutStream()
        {
            int count =0;
            for (int interger :list)
            {
                if(interger %2 ==0)
                {
                    count++;
                }
            }
            System.out.println("WithoutStrem -> Phan tu so chan la: "+count);
        }
        // Dung Stream
    public void withStream()
    {
        long count =list.stream().filter(nums ->nums%2==0).count();
        System.out.println("WithStream -> phan tu chan la:"+count);
    }

    public static void main(String[] args) {
        StreamExam streamExam= new StreamExam();
        streamExam.withoutStream();
        streamExam.withStream();
    }

}

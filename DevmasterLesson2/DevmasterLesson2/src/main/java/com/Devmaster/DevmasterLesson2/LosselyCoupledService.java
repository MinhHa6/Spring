package com.Devmaster.DevmasterLesson2;

import java.util.Arrays;

interface Sortalgorithm
{
    void sort (int []arr);
}
class LooselyBusort implements Sortalgorithm
{
    @Override
    public void sort(int arr [])
    {
        System.out.println("Sorted using buble sort");
        Arrays.stream(arr).sorted().forEach(System.out::println);
    }
}
public class LosselyCoupledService {
    private Sortalgorithm sortalgorithm;
    public LosselyCoupledService (){};
    public  LosselyCoupledService (Sortalgorithm sortalgorithm){
        this.sortalgorithm=sortalgorithm;
    }
    public void complexBusiness (int arr [])
    {
        sortalgorithm.sort(arr);
    }

    public static void main(String[] args) {
        LosselyCoupledService ssortaloservice = new LosselyCoupledService(new LooselyBusort());
        ssortaloservice.complexBusiness(new int []{11,21,13,42,15});

    }
}

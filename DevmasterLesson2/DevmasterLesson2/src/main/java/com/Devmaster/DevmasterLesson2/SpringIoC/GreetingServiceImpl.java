package com.Devmaster.DevmasterLesson2.SpringIoC;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService{
    @Override
    public String greet(String name)
    {
        return "<h2> Devmaster [String Bot!] xin chao</h2>"+"<h1 Style='color:red;text-align:center'>"+name;
    }
}

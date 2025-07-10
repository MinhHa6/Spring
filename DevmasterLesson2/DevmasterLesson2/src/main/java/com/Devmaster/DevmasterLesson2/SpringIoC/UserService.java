package com.Devmaster.DevmasterLesson2.SpringIoC;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String getUserDetail ()
    {
        return "<h1>User Details";
    }
}

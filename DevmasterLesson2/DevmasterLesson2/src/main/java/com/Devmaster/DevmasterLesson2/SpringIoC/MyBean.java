package com.Devmaster.DevmasterLesson2.SpringIoC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyBean {
    @Autowired
    private final Appconfig appconfig;
    public MyBean(Appconfig appconfig){
        this.appconfig=appconfig;
    }
    @GetMapping("/my-bean")
    public String myBean ()
    {
        return appconfig.appNAme();
    }
}

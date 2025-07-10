package com.Devmaster.DevmasterLesson2.SpringIoC;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyParamController {
    // Su dung @restparam
    @GetMapping("/my-param")
    public String searchUsers (@RequestParam(value = "name",required = false)String name){
        if(name== null)
        {
            return "<h2>No name provides";
        }
        return "<h1>Searching for users with name:"+name;
    }
    @GetMapping("/my-variable/{id}")
    public String getUsersbyid (@PathVariable(required = false)String id){
        return "<h1>User Id is "+id;
    }
}

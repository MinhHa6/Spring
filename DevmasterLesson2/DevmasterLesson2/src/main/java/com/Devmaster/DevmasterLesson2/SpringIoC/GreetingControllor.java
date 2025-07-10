package com.Devmaster.DevmasterLesson2.SpringIoC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingControllor {
    private final GreetingService greetingService;
    // SU dung Constructor-based Dependency Injection

    @Autowired
    public GreetingControllor (GreetingService greetingService)
    {
        this.greetingService=greetingService;
    }
    @GetMapping("/greet")
    public String greet ()
    {
        return greetingService.greet("Minh Ha");
    }
}

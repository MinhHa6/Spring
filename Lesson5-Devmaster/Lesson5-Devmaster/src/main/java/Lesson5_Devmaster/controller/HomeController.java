package Lesson5_Devmaster.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {
    @GetMapping
    public String index()
    {
        return "index";
    }
    @GetMapping("/profile")
    public String profile (Model model)
    {
        List<ProcessHandle.Info>profile = new ArrayList<>();
        // tao thong tin profile
        profile.add(new )

    }
}

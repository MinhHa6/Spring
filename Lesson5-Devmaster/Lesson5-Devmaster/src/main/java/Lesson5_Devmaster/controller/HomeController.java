package Lesson5_Devmaster.controller;

import Lesson5_Devmaster.entity.Profile;
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
        List<Profile>profile = new ArrayList<>();
        // tao thong tin profile
        profile.add(new Profile("MinhHa","ha12345","Ha6102003@gamil.com"));
        profile.add(new Profile("MinhHa1","ha12345","Ha6102003@gamil.com"));
        // dua profile vao model
        model.addSubModel("Devmaster",profile);
        return profile;

    }
}

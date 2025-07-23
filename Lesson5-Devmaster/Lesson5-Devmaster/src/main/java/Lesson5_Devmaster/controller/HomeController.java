package Lesson5_Devmaster.controller;

import Lesson5_Devmaster.entity.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        List<Profile> profileList = new ArrayList<>();
        profileList.add(new Profile("MinhHa", "ha12345", "Ha6102003@gmail.com"));
        profileList.add(new Profile("MinhHa1", "ha12345", "Ha6102003@gmail.com"));

        model.addAttribute("dev", "Devmaster");
        model.addAttribute("profiles", profileList);
        return "profile"; // Tên file profile.html hoặc profile.jsp
    }
}

package com.vuminhha.decorstore.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class Index {
    public String admin()
    {
        return "admin/index";
    }
}

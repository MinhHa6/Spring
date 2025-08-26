package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.entity.Customer;
import com.vuminhha.decorstore.entity.News;
import com.vuminhha.decorstore.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService
     newsService;
    public String listAll(Model model) {
        model.addAttribute("news",newsService.getAll());
        return "admin/new-list";
    }
    @GetMapping("/create")
    public String createForm (Model model)
    {
        model.addAttribute("new",new News());
        return "admin/new-form";
    }
    @GetMapping("/edit/{id}")
    public String editForm (Model model, @PathVariable Long id)
    {
        model.addAttribute("new",newsService.getNewById(id));
        return "admin/new-form";
    }
    @PostMapping("/create")
    public String saveNew(@ModelAttribute("new")News news)
    {
        newsService.saveNew(news);
        return "redirect:/news";
    }
    @PostMapping("/edit/{id}")
    public String updateNew (@ModelAttribute("new")News customer ,@PathVariable Long id)
    {
        customer.setId(id);
        newsService.saveNew(customer);
        return "redirect:/news";
    }
    public String deleteNew(@PathVariable Long id )
    {
        newsService.deleteNews(id);
        return "redirect:/news";
    }
}

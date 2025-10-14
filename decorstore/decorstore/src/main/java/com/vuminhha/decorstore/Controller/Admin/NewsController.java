package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService
     newsService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("newsList",newsService.getAll());
        return "admin/new-list";
    }
    @GetMapping("/create")
    public String createForm (Model model)
    {
        model.addAttribute("news",new News());
        model.addAttribute("category",categoryService.getAll());
        return "admin/new-form";
    }
    @GetMapping("/edit/{id}")
    public String editForm (Model model, @PathVariable Long id)
    {
        model.addAttribute("news",newsService.getNewById(id));
        model.addAttribute("category",categoryService.getAll());

        return "admin/new-form";
    }
    @PostMapping("/create")
    public String saveNew(@ModelAttribute("new") News news, BindingResult result,
                          @RequestParam("imageFile") MultipartFile file) {
        if (result.hasErrors()) {
            return "admin/new-form"; // quay lại form kèm lỗi
        }

        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String uploadDir = "uploads/"; // đường dẫn thư mục lưu ảnh
            try {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Files.copy(file.getInputStream(), uploadPath.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);
                news.setImage("/" + uploadDir + fileName); // lưu đường dẫn vào DB
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        newsService.saveNew(news);
        return "redirect:/news";
    }

    @PostMapping("/edit/{id}")
    public String updateNew (@ModelAttribute("news")News customer ,@PathVariable Long id)
    {
        customer.setId(id);
        newsService.saveNew(customer);
        return "redirect:/news";
    }
    @GetMapping("/delete/{id}")
    public String deleteNews(@PathVariable Long id )
    {
        newsService.deleteNews(id);
        return "redirect:/news";
    }
}

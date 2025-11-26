package com.vuminhha.decorstore.Controller.admin;

import com.vuminhha.decorstore.entity.BlogCategory;
import com.vuminhha.decorstore.entity.BlogPost;
import com.vuminhha.decorstore.service.news.PostCategoryService;
import com.vuminhha.decorstore.service.news.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blogs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogPostController {
     PostCategoryService postCategoryService;
     PostService postService;
    // Ds tat ca blog
    @GetMapping
    public String listBlogs(Model model)
    {
        model.addAttribute("blogs",postService.getAll());
        return "admin/blog-list";
    }
    // Hien thi form them moi blog
    @GetMapping("/create")
    public  String showCreateBlogs(Model model)
    {
        model.addAttribute("blog",new BlogPost());
        model.addAttribute("categories",postCategoryService.getAll());
        return "admin/blog-form";
    }
    // Luu blog  moi
    @PostMapping("/save")
    public String saveBlog(@ModelAttribute("blog")BlogPost blogPost)
    {
        postService.saveBlog(blogPost);
        return "redirect:/blogs";
    }
    //Hien thi form chinh sua blog
    @GetMapping("/edit/{id}")
    public String formEditBlog(@PathVariable("id")Long id,Model model)
    {
        BlogPost blogPost=postService.getBlogById(id);
        List<BlogCategory>categories=postCategoryService.getAll();
        if(blogPost == null)
        {
            return "redirect:/blogs";
        }
        model.addAttribute("blog",blogPost);
        model.addAttribute("categories",categories);
        return "admin/blog-form";
    }
    // xoa blog theo Id
    @GetMapping("/delete/{id}")
    public String deleteBlog(@PathVariable("id")Long id)
    {
        postService.deleteBlogById(id);
        return "redirect:/blogs";
    }
    @GetMapping("/toggle/{id}")
    public String toggleStatus(@PathVariable("id") Long id) {
        BlogPost blog = postService.getBlogById(id);
        if (blog != null) {
            blog.setIsActive(!blog.getIsActive());
            postService.saveBlog(blog);
        }
        return "redirect:/blogs";
    }

}

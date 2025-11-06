package com.vuminhha.decorstore.Controller.user;

import com.vuminhha.decorstore.entity.BlogCategory;
import com.vuminhha.decorstore.entity.BlogPost;
import com.vuminhha.decorstore.service.PostCategoryService;
import com.vuminhha.decorstore.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostCategoryService postCategoryService;
    private static final Logger log = LoggerFactory.getLogger(BlogController.class);


    /**
     * Danh sách tất cả bài viết
     */
    @GetMapping
    public String blogList(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "6") int size,
                           Model model) {
        try {
            // Lấy tất cả bài viết (active)
            List<BlogPost> allPosts = postService.getAllActive();

            // Phân trang thủ công
            int start = page * size;
            int end = Math.min(start + size, allPosts.size());
            List<BlogPost> posts = allPosts.subList(start, end);

            // Lấy danh mục
            List<BlogCategory> categories = postCategoryService.getAll();

            // Bài viết mới nhất (sidebar)
            List<BlogPost> recentPosts = allPosts.stream()
                    .limit(5)
                    .toList();

            model.addAttribute("posts", posts);
            model.addAttribute("categories", categories);
            model.addAttribute("recentPosts", recentPosts);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", (int) Math.ceil((double) allPosts.size() / size));

            return "users/blog-list";

        } catch (Exception e) {
            log.error("Error loading blog list: ", e);
            return "redirect:/home?error=blog_load_failed";
        }
    }

    /**
     * Chi tiết bài viết
     */
    @GetMapping("/{id}")
    public String blogDetail(@PathVariable Long id, Model model) {
        try {
            BlogPost post = postService.getBlogById(id);

            if (!post.getActive()) {
                return "redirect:/blog?error=post_not_found";
            }

            // Lấy bài viết liên quan (cùng category)
            List<BlogPost> relatedPosts = postService.getByCategory(post.getCategory().getId())
                    .stream()
                    .filter(p -> !p.getId().equals(id) && p.getActive())
                    .limit(3)
                    .toList();

            // Lấy danh mục
            List<BlogCategory> categories = postCategoryService.getAll();

            model.addAttribute("post", post);
            model.addAttribute("relatedPosts", relatedPosts);
            model.addAttribute("categories", categories);

            return "users/blog-detail";

        } catch (Exception e) {
            log.error("Error loading blog detail: ", e);
            return "redirect:/blog?error=post_not_found";
        }
    }

    /**
     * Lọc theo category
     */
    @GetMapping("/category/{categoryId}")
    public String blogByCategory(@PathVariable Long categoryId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "6") int size,
                                 Model model) {
        try {
            BlogCategory category = postCategoryService.getById(categoryId);
            List<BlogPost> allPosts = postService.getByCategory(categoryId);

            // Phân trang
            int start = page * size;
            int end = Math.min(start + size, allPosts.size());
            List<BlogPost> posts = allPosts.subList(start, end);

            List<BlogCategory> categories = postCategoryService.getAll();

            model.addAttribute("posts", posts);
            model.addAttribute("categories", categories);
            model.addAttribute("currentCategory", category);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", (int) Math.ceil((double) allPosts.size() / size));

            return "users/blog-list";

        } catch (Exception e) {
            log.error("Error loading blog by category: ", e);
            return "redirect:/blog?error=category_not_found";
        }
    }

    /**
     * Tìm kiếm blog
     */
    @GetMapping("/search")
    public String searchBlog(@RequestParam String keyword,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "6") int size,
                             Model model) {
        try {
            List<BlogPost> allPosts = postService.searchByKeyword(keyword);

            // Phân trang
            int start = page * size;
            int end = Math.min(start + size, allPosts.size());
            List<BlogPost> posts = allPosts.subList(start, end);

            List<BlogCategory> categories = postCategoryService.getAll();

            model.addAttribute("posts", posts);
            model.addAttribute("categories", categories);
            model.addAttribute("keyword", keyword);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", (int) Math.ceil((double) allPosts.size() / size));

            return "users/blog-list";

        } catch (Exception e) {
            log.error("Error searching blog: ", e);
            return "redirect:/blog?error=search_failed";
        }
    }
}

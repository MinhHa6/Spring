package com.vuminhha.decorstore.Controller.admin;

import com.vuminhha.decorstore.entity.Category;
import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.service.CategoryService;
import com.vuminhha.decorstore.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    /**
     * Hien thi ds san pham
     */
    @GetMapping
    public String listProduct(Model model) {
        model.addAttribute("products", productService.getAll());
        return "admin/product-list";
    }

    /**
     *Hien thi form tao moi
     */
    @GetMapping("/create")
    public String createProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "admin/product-form";
    }

    /**
     *Hien thi form chinh sua san pham
     */
    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        return "admin/product-form";
    }

    /**
     * Lưu sản phẩm (thêm mới hoặc cập nhật)
      */
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam("imageFile") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String uploadDir = new File("src/main/resources/static/uploads/").getAbsolutePath();
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDir, fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());

                // Lưu tên file vào trường mainImage
                product.setMainImage(fileName);
            } else {
                // Nếu không upload ảnh mới, giữ nguyên ảnh cũ
                if (product.getId() != null) {
                    Product existing = productService.getProductId(product.getId());
                    if (existing != null) {
                        product.setMainImage(existing.getMainImage());
                    }
                }
            }

            productService.saveProduct(product);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/product";
    }

    /**
     *   Xóa sản phẩm theo ID
      */
    @GetMapping("/delete/{id}")
    public String deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/product";
    }

    /**
     * Tìm kiếm sản phẩm theo tên
      */

    @GetMapping("/search")
    public String searchByProduct(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = productService.searchByName(keyword);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "admin/product-list";
    }

    /**
     *  Tìm kiếm theo danh mục
      */
    @GetMapping("/category/{id}")
    public String searchByCategory(@PathVariable("id") Long id, Model model) {
        List<Product> products = productService.findByCategory(id);
        model.addAttribute("products", products);
        return "admin/product-list";
    }
}

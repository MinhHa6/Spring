package com.vuminhha.decorstore.Controller.admin;

import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.entity.ProductImage;
import com.vuminhha.decorstore.service.product.ProductImagesService;
import com.vuminhha.decorstore.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/productImg/{productId}")  //  Đã thêm {productId}
public class ImgProductController {
    @Autowired
    public ProductImagesService productImagesService;

    @Autowired
    public ProductService productService;

    /**
     * Hien thi ds anh cua 1 san pham
     */
    @GetMapping
    public String listProductImages(@PathVariable("productId") Long productId, Model model) {
        Product product = productService.getProductId(productId);
        List<ProductImage> images = productImagesService.getByProductId(productId);
        model.addAttribute("product", product);
        model.addAttribute("images", images);
        model.addAttribute("newImage", new ProductImage());
        return "admin/product-img-list";
    }

    /**
     * Hiển thị form thêm ảnh
     */
    @GetMapping("/add")
    public String showAddForm(@PathVariable("productId") Long productId, Model model) {
        Product product = productService.getProductId(productId);
        model.addAttribute("product", product);
        model.addAttribute("newImage", new ProductImage());
        return "admin/product-img-form";
    }

    /**
     * Luu anh moi
     */
    @PostMapping("/add")
    public String addProductImage(
            @PathVariable("productId") Long productId,
            @RequestParam("name") String name,
            @RequestParam("imageFile") MultipartFile file) {

        try {
            Product product = productService.getProductId(productId);

            // Kiểm tra file có được upload không
            if (!file.isEmpty()) {
                // Thư mục lưu file
                String uploadDir = new File("src/main/resources/static/uploads/").getAbsolutePath();

                // Tạo tên file unique
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                // Tạo đường dẫn đầy đủ
                Path path = Paths.get(uploadDir, fileName);

                // Tạo thư mục nếu chưa tồn tại
                Files.createDirectories(path.getParent());

                // Lưu file
                Files.write(path, file.getBytes());

                // Tạo ProductImage entity
                ProductImage newImage = new ProductImage();
                newImage.setName(name);
                newImage.setUrlImg(fileName); // Lưu tên file
                newImage.setProduct(product);

                // Lưu vào database
                productImagesService.saveProductImages(newImage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/productImg/" + productId;
    }
    /**
     * Xoa anh theo id
     */
    @GetMapping("/deleteImg/{id}")
    public String deleteImg(@PathVariable("id") Long id, @RequestParam("productId") Long productId) {
        productImagesService.deleteProductImages(id);
        return "redirect:/productImg/" + productId;
    }

}
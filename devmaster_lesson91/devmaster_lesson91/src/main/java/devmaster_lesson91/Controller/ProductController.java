package devmaster_lesson91.controller;

import devmaster_lesson91.entity.Configuration;
import devmaster_lesson91.entity.Product;
import devmaster_lesson91.entity.ProductConfig;
import devmaster_lesson91.service.ConfigurationService;
import devmaster_lesson91.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ConfigurationService configurationService;

    private static final String UPLOAD_DIR = "src/main/resources/static/";
    private static final String UPLOAD_PATH_FILE = "images/products/";

    // ✅ Hiển thị danh sách sản phẩm
    @GetMapping
    public String listProduct(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "products/product-list";
    }

    // ✅ Hiển thị form tạo sản phẩm mới
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("configurations", configurationService.getAllConfiguration());
        return "products/product-form";
    }

    // ✅ Lưu sản phẩm mới
    @PostMapping("/new")
    public String saveProduct(
            @ModelAttribute Product product,
            @RequestParam("configurationId") List<Long> configurationIds,
            @RequestParam("configurationValue") List<String> configurationValues,
            @RequestParam("imageProduct") MultipartFile imageFile,
            Model model) {

        // Xử lý ảnh upload
        if (!imageFile.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR + UPLOAD_PATH_FILE);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = product.getName().replaceAll(" ", "_") + fileExtension;
                Path filePath = uploadPath.resolve(newFileName);

                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                product.setImgUrl("/" + UPLOAD_PATH_FILE + newFileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Gán danh sách cấu hình kèm giá trị vào sản phẩm
        List<Configuration> productConfigs = new ArrayList<>();
        for (int i = 0; i < configurationIds.size(); i++) {
            Configuration config = configurationService.getConfigurationById(configurationIds.get(i));
            Configuration pc = new Configuration();
            pc.setConfiguration(config);
            pc.setProduct(product);
            pc.setValue(configurationValues.get(i));
            productConfigs.add(pc);
        }

        product.setProductConfigs(productConfigs);
        productService.saveProduct(product);

        return "redirect:/products";
    }

    // ✅ Hiển thị form chỉnh sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("configurations", configurationService.getAllConfiguration());
        return "products/product-form";
    }

    // ✅ Xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}

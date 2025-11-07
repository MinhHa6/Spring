package com.vuminhha.decorstore.Controller.admin;

import com.vuminhha.decorstore.Controller.user.CartController;
import com.vuminhha.decorstore.entity.Customer;
import com.vuminhha.decorstore.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    /**
     * Danh sách tất cả khách hàng
     */
    @GetMapping
    public String listCustomers(Model model) {
        try {
            List<Customer> customers = customerService.getAll().stream()
                    .filter(c -> !c.getDelete()) // Chỉ lấy customer chưa xóa
                    .toList();

            model.addAttribute("customers", customers);

            log.info(" Loaded {} customers", customers.size());
            return "admin/customer-list";

        } catch (Exception e) {
            log.error(" Error loading customers: ", e);
            model.addAttribute("error", "Có lỗi khi tải danh sách khách hàng!");
            return "admin/customer-list";
        }
    }

    /**
     * Tìm kiếm khách hàng
     */
    @GetMapping("/search")
    public String searchCustomers(@RequestParam String keyword, Model model) {
        try {
            List<Customer> customers = customerService.searchCustomer(keyword);

            model.addAttribute("customers", customers);
            model.addAttribute("keyword", keyword);

            log.info("Search results for '{}': {} customers", keyword, customers.size());
            return "admin/customer-list";

        } catch (Exception e) {
            log.error(" Error searching customers: ", e);
            model.addAttribute("error", "Có lỗi khi tìm kiếm khách hàng!");
            return "admin/customer-list";
        }
    }

    /**
     * Hiển thị form tạo mới khách hàng
     */
    @GetMapping("/create")
    public String createCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "admin/customer-form";
    }

    /**
     * Hiển thị form chỉnh sửa khách hàng
     */
    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable Long id, Model model) {
        try {
            Customer customer = customerService.getCustomerById(id);
            model.addAttribute("customer", customer);

            log.info(" Loaded customer for edit: {}", id);
            return "admin/customer-form";

        } catch (Exception e) {
            log.error(" Error loading customer for edit: ", e);
            return "redirect:/customers?error=not_found";
        }
    }

    /**
     * Lưu khách hàng (tạo mới hoặc cập nhật)
     */
    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer,
                               @RequestParam(required = false) MultipartFile avatarFile,
                               RedirectAttributes redirectAttributes) {
        try {
            // Xử lý upload avatar
            if (avatarFile != null && !avatarFile.isEmpty()) {
                String avatarPath = saveFile(avatarFile);
                customer.setAvatar(avatarPath);
            } else if (customer.getId() != null) {
                // Giữ nguyên avatar cũ nếu không upload file mới
                Customer existingCustomer = customerService.getCustomerById(customer.getId());
                customer.setAvatar(existingCustomer.getAvatar());
            }


            // Lưu customer
            customerService.saveCustomer(customer);

            log.info("Customer saved successfully: {}", customer.getName());
            redirectAttributes.addFlashAttribute("success", "Lưu khách hàng thành công!");
            return "redirect:/customers";

        } catch (Exception e) {
            log.error("Error saving customer: ", e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi khi lưu khách hàng: " + e.getMessage());
            return "redirect:/customers/create";
        }
    }

    /**
     * Xóa khách hàng (soft delete)
     */
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Customer customer = customerService.getCustomerById(id);
            customer.setDelete(true);
            customer.setActive(false);
            customerService.saveCustomer(customer);

            log.info(" Customer soft deleted: {}", id);
            redirectAttributes.addFlashAttribute("success", "Xóa khách hàng thành công!");
            return "redirect:/customers";

        } catch (Exception e) {
            log.error(" Error deleting customer: ", e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi khi xóa khách hàng!");
            return "redirect:/customers";
        }
    }

    /**
     * Toggle active status
     */
    @GetMapping("/toggle-active/{id}")
    public String toggleActive(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Customer customer = customerService.getCustomerById(id);
            customer.setActive(!customer.getActive());
            customerService.saveCustomer(customer);

            log.info("Customer active status toggled: {}", id);
            redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái thành công!");
            return "redirect:/customers";

        } catch (Exception e) {
            log.error("Error toggling customer status: ", e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi khi cập nhật trạng thái!");
            return "redirect:/customers";
        }
    }

    /**
     * Xem chi tiết khách hàng
     */
    @GetMapping("/detail/{id}")
    public String customerDetail(@PathVariable Long id, Model model) {
        try {
            Customer customer = customerService.getCustomerById(id);
            model.addAttribute("customer", customer);

            log.info(" Loaded customer detail: {}", id);
            return "admin/customer-detail";

        } catch (Exception e) {
            log.error(" Error loading customer detail: ", e);
            return "redirect:/customers?error=not_found";
        }
    }
    private String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String uploadDir = "src/main/resources/static/uploads/avatars/";
        String originalFilename = file.getOriginalFilename();
        String newFilename = UUID.randomUUID() + "_" + originalFilename;

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/avatars/" + newFilename; // đường dẫn trả về để lưu trong DB
    }


}

package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.service.ProductConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product_configs")
public class ProductConfigController {
    @Autowired
    private ProductConfigService productConfigService;
    @GetMapping
    public String listProductConfig(Model model)
    {
        model.addAttribute("product_configs",productConfigService.getAll());
        return "admin/product_config-list";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model)
    {
        model.addAttribute("product_configs",new Product_Config());
        return "admin/product_config-form";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model)
    {
        model.addAttribute("product_configs",productConfigService.getProductConfig(id));
        return "admin/product_config-form";
    }
   @PostMapping("/create")
    public String saveProductConfig(@ModelAttribute ("product_configs")Product_Config config)
   {
       productConfigService.saveProductConfig(config);
       return "redirect:/product_configs";
   }
   @PostMapping("/create/{id}")
    public String updateProductConfig(@PathVariable Long id,@ModelAttribute("product_configs")Product_Config config)
   {
       config.setId(id);
       productConfigService.saveProductConfig(config);
       return "redirect:/product_configs";
   }
   @GetMapping("/delete/{id}")
    public String deleteProductConfig(@PathVariable Long id)
   {
       productConfigService.deleteProductConfig(id);
       return "redirect:/product_config";
   }
}

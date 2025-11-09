package com.vuminhha.decorstore.Controller.admin;

import com.vuminhha.decorstore.entity.Configuration;
import com.vuminhha.decorstore.service.configurations.ConfigurationsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/configuration")
public class ConfigurationController {
    private final ConfigurationsService configurationsService;

    public ConfigurationController(ConfigurationsService configurationsService) {
        this.configurationsService = configurationsService;
    }

    @GetMapping
    public String listConfigurations(Model model) {
        model.addAttribute("configurations", configurationsService.getAll());
        return "admin/configuration-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("configuration", new Configuration());
        return "admin/configuration-form";
    }

    @PostMapping("/save")
    public String saveConfiguration(@ModelAttribute("configuration") Configuration configuration) {
        configurationsService.saveConfiguration(configuration);
        return "redirect:/configuration";
    }

    @GetMapping("/edit/{id}")
    public String editConfiguration(@PathVariable Long id, Model model) {
        Configuration configuration = configurationsService.getConfigurationId(id);
        model.addAttribute("configuration", configuration);
        return "admin/configuration-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteConfiguration(@PathVariable Long id) {
        configurationsService.deleteConfiguration(id);
        return "redirect:/configuration";
    }
}

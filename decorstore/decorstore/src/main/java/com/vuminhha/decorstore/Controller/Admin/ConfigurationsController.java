package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.entity.Configurations;
import com.vuminhha.decorstore.service.ConfigurationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/configurations")
public class ConfigurationsController {
    @Autowired
    private ConfigurationsService configurationsService;
    @GetMapping
    public String listConfigurations(Model model)
    {
        model.addAttribute("configurations",configurationsService.getAll());
        return "admin/configurations-list";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model)
    {
        model.addAttribute("configurations",new Configurations());
        return "admin/configurations-form";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,Model model)
    {
        model.addAttribute("configurations",configurationsService.getConfigurationsId(id));
        return "admin/configurations-form";
    }
    @PostMapping("/create")
    public String saveConfigurations(@ModelAttribute("configurations")Configurations configurations)
    {
        configurationsService.saveConfigurations(configurations);
        return "redirect:/configurations";
    }
    @PostMapping("/create/{id}")
    public String updateConfigurations(@PathVariable Long id,@ModelAttribute Configurations configurations)
    {
        configurations.setId(id);
        configurationsService.saveConfigurations(configurations);
        return "redirect:/configurations";
    }
    @DeleteMapping
    public String deleteConfigurations(@PathVariable Long id)
    {
        configurationsService.deleteConfigurations(id);
        return "redirect:/configurations";
    }

}

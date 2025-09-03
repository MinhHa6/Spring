package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.entity.TransportMethod;
import com.vuminhha.decorstore.service.TransportMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transportMethods")
public class TransportMethodController {
    @Autowired
    private TransportMethodService  transportMethodService;
    @GetMapping
    public String getList(Model model)
    {
        model.addAttribute("transportMethods",transportMethodService.getAll());
        return "admin/transportMethod-list";
    }
    @GetMapping("/create")
    public String showForm(Model model)
    {
        model.addAttribute("transportMethod",new TransportMethod());
        return "index/transportMethod-form";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(Model model, @PathVariable Long id)
    {
        model.addAttribute("transportMethod",transportMethodService.getById(id));
        return "index/transportMethod-form";
    }
    @PostMapping("/create/{id}")
    public String saveTransport(@ModelAttribute("transportMethod")TransportMethod transportMethod,@PathVariable Long id)
    {
        transportMethodService.saveTransportMethod(transportMethod);
        return "redirect:/transportMethods";
    }
    @PostMapping("/edit/{id}")
    public String updateTransport(@ModelAttribute ("transportMethod") TransportMethod transportMethod,@PathVariable Long id)
    {
        transportMethod.setId(id);
        transportMethodService.saveTransportMethod(transportMethod);
        return "redirect:/transportMethods";
    }
    @GetMapping("/delete/{id}")
    public String deleteTransport(@PathVariable Long id)
    {
        transportMethodService.deleteTransportMethod(id);
        return "redirect:/transportMethods";
    }
}

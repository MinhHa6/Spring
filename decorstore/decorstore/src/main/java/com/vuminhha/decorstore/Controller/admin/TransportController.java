package com.vuminhha.decorstore.Controller.admin;

import com.vuminhha.decorstore.entity.TransportMethod;
import com.vuminhha.decorstore.service.transport.TransportMethodService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transport")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TransportController {
     TransportMethodService transportMethodService;
    // lay ds van chuyen
    @GetMapping
    public String listTransport(Model model)
    {
        model.addAttribute("transports",transportMethodService.getAll());
        return "admin/transport-list";
    }
    //Form them moi
    @GetMapping("/create")
    public String createFormTransport(Model model)
    {
        model.addAttribute("transportMethod",new TransportMethod());
        return "admin/transport-form";
    }
    //luu phuong thuc van chuyen
    @PostMapping("/save")
    public String saveTransport(@ModelAttribute("transportMethod")TransportMethod transportMethod)
    {
        transportMethodService.saveTransportMethod(transportMethod);
        return "redirect:/transport";
    }
    //form sua
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id")Long id,Model model)
    {
        TransportMethod transportMethod=transportMethodService.getById(id);
        if(transportMethod == null)
        {
            return "redirect:/transport";
        }
        model.addAttribute("transportMethod",transportMethod);
        return "admin/transport-form";
    }
    // xoa
    @GetMapping("/delete/{id}")
    public String deleteTransport(@PathVariable("id")Long id)
    {
        transportMethodService.deleteTransportMethod(id);
        return "redirect:/transport";
    }
    // tim kiem theo ten
    @GetMapping("/search")
    public String searchTransport(Model model, @RequestParam("keyword") String keyword) {
        List<TransportMethod> transports = transportMethodService.searchByName(keyword);
        model.addAttribute("transports", transports);
        model.addAttribute("keyword", keyword);
        return "admin/transport-list";
    }
}

package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.entity.PaymentMethod;
import com.vuminhha.decorstore.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("paymentMethods")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;
    @RequestMapping
    public String listPayment(Model model)
    {
        model.addAttribute("paymentMethods",paymentMethodService.getAll());
        return "admin/paymentMethod-list";
    }
    @RequestMapping("/create")
    public String showForm(Model model)
    {
        model.addAttribute("paymentMethod",new PaymentMethod());
        return "admin/paymentMethod-form";
    }
    @RequestMapping("/edit/{id}")
    public String showFormEdit(Model model, @PathVariable Long id)
    {
        model.addAttribute("paymentMethod",paymentMethodService.getById(id));
        return "redirect:/paymentMethods";
    }
    @PostMapping("/create")
    public String savePaymentMethod(@ModelAttribute("paymentMethod")PaymentMethod paymentMethod)
    {
        paymentMethodService.savePaymentMethod(paymentMethod);
        return "redirect:/paymentMethods";
    }
    @PostMapping("/edit/{id}")
    public String updatePaymentMethod(@PathVariable Long id,@ModelAttribute("paymentMethod")PaymentMethod paymentMethod)
    {
        paymentMethod.setId(id);
        paymentMethodService.savePaymentMethod(paymentMethod);
        return "redirect:/paymentMethods";
    }
    @GetMapping("/delete/{id}")
    public  String deletePaymentMethod(@PathVariable Long id)
    {
        paymentMethodService.deletePaymentMethod(id);
        return "redirect:/paymentMethods";
    }
}

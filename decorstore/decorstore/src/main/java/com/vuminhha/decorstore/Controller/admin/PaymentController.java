package com.vuminhha.decorstore.Controller.admin;

import com.vuminhha.decorstore.entity.PaymentMethod;
import com.vuminhha.decorstore.service.payment.PaymentMethodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    public final PaymentMethodService paymentMethodService;
    public PaymentController(PaymentMethodService paymentMethodService)
    {
        this.paymentMethodService=paymentMethodService;
    }
    @GetMapping
    public String listPayment(Model model)
    {
        model.addAttribute("payments",paymentMethodService.getAll());
        return "admin/payment-list";
    }
    // hien thi form them moi
    @GetMapping("/create")
    public  String createPaymentForm(Model model)
    {
        model.addAttribute("paymentMethod",new PaymentMethod());
        return "admin/payment-form";
    }
    //Luu phuong thuc thanh toan,update
    @PostMapping("/save")
    public String savePayment(@ModelAttribute("payment")PaymentMethod paymentMethod)
    {
        paymentMethodService.savePaymentMethod(paymentMethod);
        return "redirect:/payment";
    }
    // form sua
    @GetMapping("/edit/{id}")
    public String editPaymentForm(Model model, @PathVariable("id")Long id)
    {
        PaymentMethod paymentMethod=paymentMethodService.getById(id);
        if(paymentMethod==null)
        {
            return "redirect:/payment";
        }
        model.addAttribute("paymentMethod",paymentMethod);
        return "admin/payment-form";
    }
    // Xoa phuong thuc thanh toan
    @GetMapping("/delete/{id}")
    public String deletePayment(Model model,@PathVariable("id")Long id)
    {
        paymentMethodService.deletePaymentMethod(id);
        return "redirect:/payment";
    }
    // Tim kiem phuong thuc thanh toan theo ten
    @GetMapping("/search")
    public String searchPayment(@RequestParam("keyword")String keyword,Model model)
    {
        List<PaymentMethod>paymentMethods=paymentMethodService.searchByName(keyword);
        model.addAttribute("payments",paymentMethods);
        model.addAttribute("keyword",keyword);
        return "admin/payment-list";
    }
}

package com.vuminhha.decorstore.Controller.admin;

import com.vuminhha.decorstore.dto.DashboardStatisticsDto;
import com.vuminhha.decorstore.dto.OrderStatusStatistics;
import com.vuminhha.decorstore.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public String index(Model model) {
        try {
            DashboardStatisticsDto stats = dashboardService.getDashboardStatistics();

            // Truyền dữ liệu vào model
            model.addAttribute("userCount", stats.getTotalCustomers());
            model.addAttribute("newCustomersCount", stats.getNewCustomersThisMonth());
            model.addAttribute("totalSales", stats.getTotalRevenue());
            model.addAttribute("salesThisMonth", stats.getRevenueThisMonth());
            model.addAttribute("revenueGrowth", stats.getRevenueGrowth());
            model.addAttribute("orderCount", stats.getTotalOrders());
            model.addAttribute("ordersThisMonth", stats.getOrdersThisMonth());
            model.addAttribute("averageOrderValue", stats.getAverageOrderValue());

            model.addAttribute("orderStatus", stats.getOrderStatus());
            model.addAttribute("revenues", stats.getMonthlyRevenue());
            model.addAttribute("topProducts", stats.getTopProducts());
            model.addAttribute("newUsers", stats.getRecentCustomers());

        } catch (Exception e) {
            // Log error và set giá trị mặc định
            e.printStackTrace();
            model.addAttribute("userCount", 0L);
            model.addAttribute("newCustomersCount", 0L);
            model.addAttribute("totalSales", java.math.BigDecimal.ZERO);
            model.addAttribute("salesThisMonth", java.math.BigDecimal.ZERO);
            model.addAttribute("revenueGrowth", java.math.BigDecimal.ZERO);
            model.addAttribute("orderCount", 0L);
            model.addAttribute("ordersThisMonth", 0L);
            model.addAttribute("averageOrderValue", java.math.BigDecimal.ZERO);
            model.addAttribute("orderStatus", new OrderStatusStatistics(0L, 0L, 0L));
            model.addAttribute("revenues", java.util.Collections.emptyList());
            model.addAttribute("topProducts", java.util.Collections.emptyList());
            model.addAttribute("newUsers", java.util.Collections.emptyList());
            model.addAttribute("error", "Không thể tải dữ liệu dashboard: " + e.getMessage());
        }

        return "admin/index";
    }
}
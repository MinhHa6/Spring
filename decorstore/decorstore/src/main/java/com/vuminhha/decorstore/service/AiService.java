package com.vuminhha.decorstore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AiService {

    private static final Logger log = LoggerFactory.getLogger(AiService.class);
    private final Random random = new Random();

    /**
     * MOCK SERVICE - Không gọi API, chỉ trả response giả
     * Dùng để test UI khi không kết nối được mạng
     */
    public String generateContent(String prompt) {
        log.info("Mock AI nhận prompt: {}", prompt);

        // Simulate thinking time
        try {
            Thread.sleep(500 + random.nextInt(1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return generateMockResponse(prompt);
    }

    private String generateMockResponse(String prompt) {
        String lowerPrompt = prompt.toLowerCase();

        // Lời chào
        if (lowerPrompt.matches(".*(xin chào|chào|hi|hello|hey).*")) {
            return "Xin chào! 👋 Tôi là trợ lý AI của Decor Store. Tôi có thể giúp bạn:\n" +
                    "• Tìm hiểu về sản phẩm (đèn, sofa, tranh, bình hoa)\n" +
                    "• Tư vấn trang trí không gian\n" +
                    "• Thông tin về giá cả và giao hàng\n\n" +
                    "Bạn cần hỗ trợ gì ạ?";
        }

        // Cảm ơn
        if (lowerPrompt.matches(".*(cảm ơn|cám ơn|thanks|thank you).*")) {
            return "Rất vui được hỗ trợ bạn! 😊 Nếu còn thắc mắc gì, đừng ngại hỏi nhé!";
        }

        // Về giá
        if (lowerPrompt.contains("giá") || lowerPrompt.contains("bao nhiêu") || lowerPrompt.contains("tiền")) {
            return "💰 Giá sản phẩm của chúng tôi rất đa dạng:\n" +
                    "• Đèn decor: từ 300.000₫ - 3.000.000₫\n" +
                    "• Ghế sofa: từ 5.000.000₫ - 20.000.000₫\n" +
                    "• Tranh treo tường: từ 200.000₫ - 2.000.000₫\n\n" +
                    "🚚 Miễn phí ship cho đơn từ 2.000.000₫!";
        }

        // Về giao hàng
        if (lowerPrompt.contains("giao") || lowerPrompt.contains("ship") || lowerPrompt.contains("vận chuyển")) {
            return "🚚 Thông tin giao hàng:\n" +
                    "• Giao hàng toàn quốc\n" +
                    "• MIỄN PHÍ với đơn từ 2.000.000₫\n" +
                    "• Thời gian: 2-5 ngày tùy khu vực\n" +
                    "• Đóng gói cẩn thận, bảo hành sản phẩm";
        }

        // Về đèn
        if (lowerPrompt.contains("đèn")) {
            return "💡 Bộ sưu tập đèn decor của chúng tôi:\n" +
                    "• Đèn thả trần - phong cách hiện đại\n" +
                    "• Đèn bàn - ánh sáng ấm áp\n" +
                    "• Đèn cây - trang trí góc phòng\n" +
                    "• Đèn LED - tiết kiệm điện\n\n" +
                    "Bạn cần đèn cho không gian nào ạ?";
        }

        // Về sofa/ghế
        if (lowerPrompt.contains("sofa") || lowerPrompt.contains("ghế")) {
            return "🛋️ Ghế sofa cao cấp:\n" +
                    "• Sofa 2-3 chỗ ngồi\n" +
                    "• Chất liệu vải/da cao cấp\n" +
                    "• Thiết kế hiện đại, sang trọng\n" +
                    "• Màu sắc đa dạng: xám, be, xanh navy...\n\n" +
                    "Bạn muốn xem mẫu nào?";
        }

        // Về tranh
        if (lowerPrompt.contains("tranh")) {
            return "🖼️ Tranh treo tường:\n" +
                    "• Tranh canvas nghệ thuật\n" +
                    "• Tranh khung gỗ cao cấp\n" +
                    "• Nhiều kích thước: 40x60cm, 60x90cm, 80x120cm\n" +
                    "• Phong cách: minimalist, abstract, vintage\n\n" +
                    "Bạn thích phong cách nào?";
        }

        // Về tư vấn
        if (lowerPrompt.contains("tư vấn") || lowerPrompt.contains("giúp")) {
            return "🎨 Tôi rất vui được tư vấn!\n\n" +
                    "Để tư vấn tốt nhất, bạn có thể chia sẻ:\n" +
                    "• Không gian nào? (phòng khách, phòng ngủ...)\n" +
                    "• Diện tích bao nhiêu?\n" +
                    "• Phong cách yêu thích? (hiện đại, vintage, tối giản...)\n" +
                    "• Ngân sách dự kiến?";
        }

        // Về sản phẩm chung
        if (lowerPrompt.contains("sản phẩm") || lowerPrompt.contains("có gì") || lowerPrompt.contains("bán")) {
            return "🏠 Decor Store có:\n\n" +
                    "✨ Đèn trang trí (thả, bàn, cây, LED)\n" +
                    "✨ Ghế & Sofa cao cấp\n" +
                    "✨ Tranh treo tường nghệ thuật\n" +
                    "✨ Bình hoa & decor nhỏ\n" +
                    "✨ Phụ kiện trang trí nội thất\n\n" +
                    "Bạn quan tâm loại nào?";
        }

        // Response mặc định
        String[] defaults = {
                "Cảm ơn bạn đã liên hệ! Để tôi hỗ trợ tốt hơn, bạn có thể hỏi cụ thể về:\n" +
                        "• Sản phẩm (đèn, sofa, tranh...)\n" +
                        "• Giá cả và khuyến mãi\n" +
                        "• Giao hàng và thanh toán\n" +
                        "• Tư vấn trang trí không gian",

                "Tôi có thể giúp bạn tìm hiểu về sản phẩm decor nội thất của chúng tôi. " +
                        "Bạn đang tìm kiếm sản phẩm nào? (đèn, ghế sofa, tranh, hay bình hoa?)",

                "Decor Store chuyên cung cấp đồ trang trí nội thất cao cấp. " +
                        "Bạn muốn biết về sản phẩm nào, hoặc cần tư vấn trang trí không gian?"
        };

        return defaults[random.nextInt(defaults.length)];
    }
}
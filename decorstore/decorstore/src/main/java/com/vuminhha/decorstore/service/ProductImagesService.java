package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.ProductImage;
import com.vuminhha.decorstore.repository.ProductImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductImagesService {
    private final ProductImagesRepository productImagesRepository;
    public ProductImagesService (ProductImagesRepository productImagesRepository)
    {
        this.productImagesRepository=productImagesRepository;
    }

    // Lay tat ca anh theo ProductId
    public List<ProductImage> getByProductId(Long productId)
    {
        return productImagesRepository.findByProductId(productId);
    }
    //them hoac cap nhat anh moi
    public ProductImage saveProductImages(ProductImage productImage)
    {
        return productImagesRepository.save(productImage);
    }
    //xoa anh theo Id
    public void deleteProductImages(Long id)
    {
        ProductImage img = productImagesRepository.findById(id).orElseThrow();

        Path path = Paths.get("uploads/" + img.getUrlImg());
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            e.printStackTrace(); // log lỗi file
        }

        productImagesRepository.deleteById(id); // xoá record trong DB
    }
}

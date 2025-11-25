package com.vuminhha.decorstore.service.product.Impl;

import com.vuminhha.decorstore.entity.ProductImage;
import com.vuminhha.decorstore.repository.ProductImagesRepository;
import com.vuminhha.decorstore.service.product.ProductImagesService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductImagesServiceImpl implements ProductImagesService {
    ProductImagesRepository productImagesRepository;

    // Lay tat ca anh theo ProductId
    @Override
    public List<ProductImage> getByProductId(Long productId)
    {
        return productImagesRepository.findByProductId(productId);
    }
    //them hoac cap nhat anh moi
    @Override
    public ProductImage saveProductImages(ProductImage productImage)
    {
        return productImagesRepository.save(productImage);
    }
    //xoa anh theo Id
    @Override
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

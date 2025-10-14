package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.ProductImage;
import com.vuminhha.decorstore.repository.ProductImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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
        productImagesRepository.deleteById(id);
    }
}

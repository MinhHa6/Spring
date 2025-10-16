package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.ProductConfig;
import com.vuminhha.decorstore.repository.ProductConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductConfigService {
    private final ProductConfigRepository productConfigRepository;

    public ProductConfigService (ProductConfigRepository productConfigRepository)
    {
        this.productConfigRepository=productConfigRepository;
    }
    // lay tat ca cau hinh cua san pham
    public List<ProductConfig> getAll()
    {
        return productConfigRepository.findAll();
    }
    // Lay theo productConfig theo dia chi Id
    public ProductConfig getProductConfig (Long id)
    {
        return productConfigRepository.findById(id).orElseThrow(()->new RuntimeException("Product-config  not found with id:"+id));
    }
    //Them hoac cap nhat cau hinh
    public ProductConfig saveProductConfig(ProductConfig productConfig)
    {
        return productConfigRepository.save(productConfig);
    }
    // xoa cau hinh theo dia chi Id
    public void deleteProductConfig(Long id)
    {
        productConfigRepository.deleteById(id);
    }
    // Lay ds cau hinh theo productId
    public List<ProductConfig>getByProductId(Long productId)
    {
        return productConfigRepository.findByProductId(productId);
    }

}

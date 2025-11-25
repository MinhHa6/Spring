package com.vuminhha.decorstore.service.product.Impl;

import com.vuminhha.decorstore.entity.ProductConfig;
import com.vuminhha.decorstore.repository.ProductConfigRepository;
import com.vuminhha.decorstore.service.product.ProductConfigService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE,makeFinal = true)
public class ProductConfigServiceImpl implements ProductConfigService {
    ProductConfigRepository productConfigRepository;
    // lay tat ca cau hinh cua san pham
    @Override
    public List<ProductConfig> getAll()
    {
        return productConfigRepository.findAll();
    }
    // Lay theo productConfig theo dia chi Id
    @Override
    public ProductConfig getProductConfig (Long id)
    {
        return productConfigRepository.findById(id).orElseThrow(()->new RuntimeException("Product-config  not found with id:"+id));
    }
    //Them hoac cap nhat cau hinh
    @Override
    public ProductConfig saveProductConfig(ProductConfig productConfig)
    {
        return productConfigRepository.save(productConfig);
    }
    // xoa cau hinh theo dia chi Id
    @Override
    public void deleteProductConfig(Long id)
    {
        productConfigRepository.deleteById(id);
    }
    // Lay ds cau hinh theo productId
    @Override
    public List<ProductConfig>getByProductId(Long productId)
    {
        return productConfigRepository.findByProductId(productId);
    }
}

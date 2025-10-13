package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.repository.ProductConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductConfigService {
    @Autowired
    private ProductConfigRepository productConfigRepository;

    // lay tat ca cau hinh cua san pham
    public List<Product_Config> getAll()
    {
        return productConfigRepository.findAll();
    }
    // Lay theo id
    public Product_Config getProductConfig (Long id)
    {
        return productConfigRepository.findById(id).orElseThrow(()->new RuntimeException("Product-config null"));
    }
    //create/update
    public Product_Config saveProductConfig(Product_Config productConfig)
    {
        return productConfigRepository.save(productConfig);
    }
    // xoa cau hinh
    public void deleteProductConfig(Long id)
    {
        productConfigRepository.deleteById(id);
    }

}

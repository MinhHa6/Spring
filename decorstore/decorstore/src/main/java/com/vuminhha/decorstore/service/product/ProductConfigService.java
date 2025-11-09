package com.vuminhha.decorstore.service.product;


import com.vuminhha.decorstore.entity.ProductConfig;

import java.util.List;

public interface ProductConfigService {
    List<ProductConfig> getAll();
    ProductConfig getProductConfig (Long id);
    ProductConfig saveProductConfig(ProductConfig productConfig);
    void deleteProductConfig(Long id);
    List<ProductConfig>getByProductId(Long productId);
}

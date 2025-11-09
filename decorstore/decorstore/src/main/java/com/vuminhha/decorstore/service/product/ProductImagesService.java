package com.vuminhha.decorstore.service.product;


import com.vuminhha.decorstore.entity.ProductImage;

import java.util.List;

public interface ProductImagesService {
    public List<ProductImage> getByProductId(Long productId);
    ProductImage saveProductImages(ProductImage productImage);
    void deleteProductImages(Long id);

}

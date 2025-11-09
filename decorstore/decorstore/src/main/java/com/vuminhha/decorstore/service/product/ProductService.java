package com.vuminhha.decorstore.service.product;


import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product saveProduct (Product product);
    Product getProductId(Long id);
    void deleteProduct(Long id);
    List<Product> searchByName(String keyword);
    List<Product>findByCategory(Long categoryId);
    List<Product>getFeaturedProducts();
    List<Product>getFindTop4ByCategory_IdAndIdNot(Long categoryId,Long productId);
    Product getProductWithDetails(Long id);

}

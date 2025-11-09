package com.vuminhha.decorstore.service.product.Impl;

import com.vuminhha.decorstore.config.exception.ResourceNotFoundException;
import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.repository.ProductRepository;
import com.vuminhha.decorstore.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private  final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository)
    {
        this.productRepository=productRepository;
    }
    @Override
    public List<Product> getAll()
    {
        return productRepository.findAll();
    }
    @Override
    public Product saveProduct (Product product)
    {
        System.out.println(product);
        return productRepository.save(product);
    }
    // Lay san pham theo Id
    @Override
    public Product getProductId(Long id)
    {
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not Product"+id));
    }
    @Override
    public void deleteProduct(Long id)
    {
        productRepository.deleteById(id);
    }

    // tim kiem theo ten (Chua tu khoa khong phan biet hoa thuong )
    @Override
    public List<Product> searchByName(String keyword)
    {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    //Tim kiem theo danh muc
    @Override
    public List<Product>findByCategory(Long categoryId)
    {
        return productRepository.findByCategoryId(categoryId);
    }

    // Lay ra san pham noi bat
    @Override
    public List<Product>getFeaturedProducts()
    {
        return productRepository.findByIsFeaturedTrueAndIsActiveTrue();
    }
    // lay 4 san pham cung danh muc tru san pham hien tai
    @Override
    public List<Product>getFindTop4ByCategory_IdAndIdNot(Long categoryId,Long productId)
    {
        return productRepository.findTop4ByCategory_IdAndIdNot(categoryId,productId);
    }
    // Load product với đầy đủ ảnh và cấu hình
    @Override
    @Transactional(readOnly = true)
    public Product getProductWithDetails(Long id) {
        // Load ảnh phụ trước
        Product product = productRepository.findByIdWithImages(id)
                .orElseThrow(() -> new RuntimeException("Not Product"));

        // Load cấu hình sau (trong cùng transaction nên không bị lỗi)
        productRepository.findByIdWithConfigs(id);

        return product;
    }
}

package devmaster_lesson91.service;

import devmaster_lesson91.entity.Product;
import devmaster_lesson91.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> getAllProduct()
    {
        return productRepository.findAll();

    }
    public Product saveProduct(Product product)
    {
        return productRepository.save(product);
    }
    public Product getProductById(Long id)
    {
        return productRepository.findById(id).orElse(null);
    }
    public void deleteProduct(Long id)
    {
        productRepository.deleteById(id);
    }
}

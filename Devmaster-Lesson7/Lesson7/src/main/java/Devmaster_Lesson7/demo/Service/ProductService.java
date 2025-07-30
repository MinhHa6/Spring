package Devmaster_Lesson7.demo.Service;

import Devmaster_Lesson7.demo.entity.Product;
import Devmaster_Lesson7.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;
    public ProductService(ProductRepository productRepository)
    {
        this.productRepository=productRepository;
    }
    //Lay ra toan bo danh sach
    public List<Product>getAllProduct()
    {
        return productRepository.findAll();
    }
    //Doc du lieu bang theo id
    public Optional<Product>findbyId(Long id)
    {
        return productRepository.findById(id);
    }
    //Cap nhat du lieu
    public Product saveProduct (Product product)
    {
        System.out.println(product);
        return productRepository.save(product);
    }
    // xoa product theo id
    public void deleteProduct(Long id)
    {
        productRepository.deleteById(id);
    }
//---///
}

package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.Product_Images;
import com.vuminhha.decorstore.repository.ProductImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImagesService {
    @Autowired
    private ProductImagesRepository productImagesRepository;
     public List<Product_Images> getAll ()
     {
         return productImagesRepository.findAll();
     }
     //lay anh san pham them dia chi id
    public Product_Images getProductImagesById(Long id)
    {
        return productImagesRepository.findById(id).orElseThrow(()->new RuntimeException("null"));
    }
    //update create
    public Product_Images saveProductImages(Product_Images productImages)
    {
        return productImagesRepository.save(productImages);
    }
    //xoa anh theo dia chi Id
    public void deleteProductImages(Long id)
    {
        productImagesRepository.deleteById(id);
    }
}

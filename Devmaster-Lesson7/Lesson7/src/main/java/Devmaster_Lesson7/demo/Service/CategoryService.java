package Devmaster_Lesson7.demo.Service;

import Devmaster_Lesson7.demo.entity.Category;
import Devmaster_Lesson7.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository=categoryRepository;
    }
    //Lay danh sach
    public List<Category>getAllCategories()
    {
        System.out.println(categoryRepository.findAll());
        return categoryRepository.findAll();
    }
    //Lay categor theo id
    public Optional<Category>getCategoryId(Long id)
    {
        return categoryRepository.findById(id);
    }
    // Cap nhat du lieu bang
    public  Category saveCategory(Category category)
    {
        return categoryRepository.save(category);
    }
    //xoa theo dia chi id
    public void deleteCategory(Long id)
    {
        categoryRepository.deleteById(id);
    }
}

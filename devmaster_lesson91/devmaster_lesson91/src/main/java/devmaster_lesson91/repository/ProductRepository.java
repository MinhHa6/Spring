package devmaster_lesson91.repository;

import devmaster_lesson91.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}

package Lesson6_Datajpa.repository;

import Lesson6_Datajpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}

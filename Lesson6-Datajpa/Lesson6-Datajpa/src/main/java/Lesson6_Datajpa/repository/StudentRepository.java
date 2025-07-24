package Lesson6_Datajpa.repository;

import Lesson6_Datajpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}

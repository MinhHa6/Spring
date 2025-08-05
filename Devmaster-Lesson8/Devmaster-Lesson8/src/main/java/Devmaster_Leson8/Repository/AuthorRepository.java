package Devmaster_Leson8.Repository;

import Devmaster_Leson8.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}

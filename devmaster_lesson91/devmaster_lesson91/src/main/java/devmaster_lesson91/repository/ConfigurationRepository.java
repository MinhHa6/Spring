package devmaster_lesson91.repository;

import devmaster_lesson91.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration,Long> {
}

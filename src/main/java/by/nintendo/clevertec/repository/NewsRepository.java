package by.nintendo.clevertec.repository;

import by.nintendo.clevertec.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
}

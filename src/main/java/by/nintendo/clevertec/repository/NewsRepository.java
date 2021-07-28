package by.nintendo.clevertec.repository;

import by.nintendo.clevertec.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
    @Override
    Page<News> findAll(Pageable pageable);
}

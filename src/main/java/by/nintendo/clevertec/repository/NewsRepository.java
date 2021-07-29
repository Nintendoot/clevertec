package by.nintendo.clevertec.repository;

import by.nintendo.clevertec.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT  p FROM News p WHERE p.text LIKE %?1%" + "OR p.title LIKE %?1%" + " OR CONCAT(p.date, '') LIKE %?1%")
    List<News> findAll(String keyword);

    Page<News> findAll(Pageable pageable);
}

package by.nintendo.clevertec.service;

import by.nintendo.clevertec.model.News;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    void create(News news);

    void update(Long id, News news);

    String getAll(Pageable pageable);

    String getById(Long id, Pageable pageable);

    void deleteById(Long id);

    String search(String keyword);

    String getAllByTitle(Pageable pageable);
}

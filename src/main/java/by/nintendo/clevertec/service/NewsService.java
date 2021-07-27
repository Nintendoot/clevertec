package by.nintendo.clevertec.service;

import by.nintendo.clevertec.model.News;
import by.nintendo.clevertec.dto.NewsDto;

public interface NewsService {
    NewsDto createOrUpdate(News news);

    String getAll();

    String  getById(Long id);

    void deleteById(Long id);
}

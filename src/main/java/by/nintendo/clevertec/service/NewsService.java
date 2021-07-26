package by.nintendo.clevertec.service;

import by.nintendo.clevertec.model.News;
import by.nintendo.clevertec.dto.NewsDto;

import java.util.List;

public interface NewsService {
    NewsDto create(News news);
    void update(News news);

    List<NewsDto> getAll();

    NewsDto  getById(Long id);

    void deleteById(Long id);
}

package by.nintendo.clevertec.service.impl;

import by.nintendo.clevertec.model.News;
import by.nintendo.clevertec.dto.NewsDto;
import by.nintendo.clevertec.repository.NewsRepository;
import by.nintendo.clevertec.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsImplService implements NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Override
    public NewsDto create(News news) {
        news.setDate(LocalDate.now());
        newsRepository.save(news);
        return NewsDto.newBuilder().setDate(news.getDate().toString()).setId(news.getId()).setText(news.getText()).setTitle(news.getTitle()).build();
    }

    @Override
    public void update(News news) {

    }

    @Override
    public List<NewsDto> getAll() {
        List<NewsDto> list=new ArrayList<>();
for(News news:newsRepository.findAll()){
    NewsDto build = NewsDto.newBuilder().setDate(news.getDate().toString()).setId(news.getId()).setText(news.getText()).setTitle(news.getTitle()).build();

list.add(build);
}
        return list;
    }

    @Override
    public NewsDto getById(Long id) {
        News news = newsRepository.getById(id);
        return NewsDto.newBuilder().setDate(news.getDate().toString()).setId(news.getId()).setText(news.getText()).setTitle(news.getTitle()).build();


    }

    @Override
    public void deleteById(Long id) {
newsRepository.deleteById(id);
    }
}

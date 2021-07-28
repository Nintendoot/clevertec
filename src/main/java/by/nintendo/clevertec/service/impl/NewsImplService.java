package by.nintendo.clevertec.service.impl;

import by.nintendo.clevertec.exception.NewsNotFoundException;
import by.nintendo.clevertec.model.Comment;
import by.nintendo.clevertec.model.News;
import by.nintendo.clevertec.dto.NewsDto;
import by.nintendo.clevertec.repository.NewsRepository;
import by.nintendo.clevertec.service.NewsService;
import by.nintendo.clevertec.util.NewsBuilder;
import by.nintendo.clevertec.util.converter.ProtoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NewsImplService implements NewsService {

    private final ProtoConverter protoConverter;
    private final NewsBuilder newsBuilder;
    private final NewsRepository newsRepository;

    public NewsImplService(NewsBuilder newsBuilder, NewsRepository newsRepository, ProtoConverter protoConverter) {
        this.newsBuilder = newsBuilder;
        this.newsRepository = newsRepository;
        this.protoConverter = protoConverter;
    }


    @Override
    public void create(News news) {
        news.setDate(LocalDate.now());
        newsRepository.save(news);
        log.info("In create - news: {} ", news);
    }

    @Override
    public void update(Long id, News news) throws RuntimeException {
        Optional<News> byId = newsRepository.findById(id);
        if (byId.isPresent()) {
            byId.get().setDate(LocalDate.now());
            byId.get().setTitle(news.getTitle());
            byId.get().setText(news.getText());
            newsRepository.save(byId.get());
            log.info("In update - news: {} update by id: {}", byId, id);
        } else {
            throw new NewsNotFoundException(String.format("News with id: %s not found.", id));
        }
    }

    @Override
    public String getAll(Pageable pageable) {
        Page<News> all = newsRepository.findAll(pageable);
        List<NewsDto> collect = all.stream().map(newsBuilder::toDtoNews).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (NewsDto newsDto : collect) {
            stringBuilder.append(protoConverter.objectToJson(newsDto));
        }
        log.info("In getAll - pageNumber: {} pageSize: {}", pageable.getPageNumber(), pageable.getPageSize());
        return stringBuilder.toString();
    }

    @Override
    public String getById(Long id, Pageable pageable) throws RuntimeException {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            log.info("In getById - id:{} user found: {}", id, news);
            List<Comment> collect = news.get().getComments().stream()
                    .skip(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .collect(Collectors.toList());
            news.get().setComments(collect);
            return protoConverter.objectToJson(newsBuilder.toDtoNews(news.get()));
        } else {
            throw new NewsNotFoundException(String.format("News with id: %s not found.", id));
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            newsRepository.deleteById(id);
            log.info("In deleteById - id: {}", id);
        } else {
            throw new NewsNotFoundException(String.format("News with id: %s not found.", id));
        }
    }
}

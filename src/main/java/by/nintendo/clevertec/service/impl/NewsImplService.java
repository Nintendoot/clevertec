package by.nintendo.clevertec.service.impl;

import by.nintendo.clevertec.model.News;
import by.nintendo.clevertec.dto.NewsDto;
import by.nintendo.clevertec.repository.NewsRepository;
import by.nintendo.clevertec.service.NewsService;
import by.nintendo.clevertec.util.NewsBuilder;
import by.nintendo.clevertec.util.converter.ProtoConverter;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public NewsDto createOrUpdate(News news) {
        news.setDate(LocalDate.now());
        newsRepository.save(news);
        return newsBuilder.toDtoNews(news);
    }

    @Override
    public String getAll() {
        List<News> all = newsRepository.findAll();
        List<NewsDto> collect = all.stream().map(x -> newsBuilder.toDtoNews(x)).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (NewsDto newsDto : collect) {
            stringBuilder.append(protoConverter.objectToJson(newsDto));
        }
        return stringBuilder.toString();
    }

    @Override
    public String getById(Long id) {
        News byId = newsRepository.getById(id);
        try {
            return JsonFormat.printer().print(newsBuilder.toDtoNews(byId));
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}

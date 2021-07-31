package by.nintendo.clevertec.service.impl;

import by.nintendo.clevertec.dto.NewsDtoTitle;
import by.nintendo.clevertec.exception.NewsNotFoundException;
import by.nintendo.clevertec.model.Comment;
import by.nintendo.clevertec.model.News;
import by.nintendo.clevertec.dto.NewsDto;
import by.nintendo.clevertec.repository.NewsRepository;
import by.nintendo.clevertec.service.NewsService;
import by.nintendo.clevertec.util.NewsBuilder;
import by.nintendo.clevertec.util.converter.ProtoConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A service that processes requests related to news
 */

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

    /**
     * Creating a news
     *
     * @param news object
     */

    @Override
    public void create(News news) {
        news.setDate(LocalDate.now());
        newsRepository.save(news);
    }


    /**
     * Updating a news
     *
     * @param id   news
     * @param news object
     */

    @Override
    public void update(Long id, News news) throws RuntimeException {
        Optional<News> byId = newsRepository.findById(id);
        if (byId.isPresent()) {
            byId.get().setDate(LocalDate.now());
            byId.get().setTitle(news.getTitle());
            byId.get().setText(news.getText());
            newsRepository.save(byId.get());
        } else {
            throw new NewsNotFoundException(String.format("News with id: %s not found.", id));
        }
    }


    /**
     * Allows you to get a list of news with a paginated view
     *
     * @param pageable from the request
     * @return list of news with pagination
     */

    @Override
    public String getAll(Pageable pageable) {
        Page<News> all = newsRepository.findAll(pageable);
        List<NewsDto> collect = all.stream().map(newsBuilder::toDtoNews).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (NewsDto newsDto : collect) {
            stringBuilder.append(protoConverter.objectToJson(newsDto));
        }
        return stringBuilder.toString();
    }


    /**
     * Allows you to get news by id with a paginated view
     *
     * @param id       news
     * @param pageable from the request
     * @return json  news with comment pagination
     */

    @Override
    public String getById(Long id, Pageable pageable) throws RuntimeException {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
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


    /**
     * Deleted News
     *
     * @param id news
     */

    @Override
    public void deleteById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            newsRepository.deleteById(id);
        } else {
            throw new NewsNotFoundException(String.format("News with id: %s not found.", id));
        }
    }


    /**
     * Full-text search for various news parameters
     *
     * @param keyword from the request
     * @return json found news
     */

    @Override
    public String search(String keyword) {
        List<News> all = newsRepository.findAll(keyword);
        if (!all.isEmpty()) {
            List<NewsDto> list = newsBuilder.toListNewsDto(all);
            StringBuilder stringBuilder = new StringBuilder();
            for (NewsDto newsDto : list) {
                stringBuilder.append(protoConverter.objectToJson(newsDto));
            }
            return stringBuilder.toString();
        } else {
            return "Nothing was found for your parameters.";
        }
    }


    /**
     * Allows you to get a list of news with a paginated view
     *
     * @param pageable from the request
     * @return json of news with pagination
     */

    @Override
    public String getAllByTitle(Pageable pageable) {
        Page<News> all = newsRepository.findAll(pageable);
        List<NewsDtoTitle> newsDtoTitles = newsBuilder.toListNewsDtoTitle(all);
        StringBuilder stringBuilder = new StringBuilder();
        for (NewsDtoTitle n : newsDtoTitles) {
            stringBuilder.append(protoConverter.objectToJson(n));
        }
        return stringBuilder.toString();
    }
}

package by.nintendo.clevertec.util;

import by.nintendo.clevertec.dto.CommentDto;
import by.nintendo.clevertec.dto.NewsDto;
import by.nintendo.clevertec.dto.NewsDtoTitle;
import by.nintendo.clevertec.dto.newsListComment;
import by.nintendo.clevertec.model.Comment;
import by.nintendo.clevertec.model.News;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Converter News to Proto classes
 */
@Component
public class NewsBuilder {

    private final CommentBuilder commentBuilder;

    public NewsBuilder(CommentBuilder commentBuilder) {
        this.commentBuilder = commentBuilder;
    }
    /**
     * creating a proto dto on the news
     *
     * @param news object
     * @return proto dto(NewsDto)
     */
    public NewsDto toDtoNews(News news) {
        NewsDto newsDto = NewsDto.newBuilder()
                .setDate(news.getDate().toString())
                .setId(news.getId())
                .setText(news.getText())
                .setTitle(news.getTitle())
                .setComments(newsListComment.newBuilder().addAllComments(newsListComment(news.getComments())).build())
                .build();
        return newsDto;
    }
    /**
     * creating a list proto dto on the news comment list
     *
     * @param  list comment object
     * @return list proto dto
     */
    public List<CommentDto> newsListComment(List<Comment> list) {
        return list.stream()
                .map(commentBuilder::toCommentDto)
                .collect(Collectors.toList());
    }
    /**
     * creating a list proto dto on the list News
     *
     * @param  news list
     * @return list proto dto
     */
    public List<NewsDto> toListNewsDto(List<News> news){
        return news.stream().map(this::toDtoNews).collect(Collectors.toList());
    }
    /**
     * creating a proto dto(NewsDtoTitle) on the News
     *
     * @param  news object
     * @return  proto dto(NewsDtoTitle)
     */
    public NewsDtoTitle toDtoNewsTitle(News news) {
        NewsDtoTitle newsDtoTitle = NewsDtoTitle.newBuilder()
                .setDate(news.getDate().toString())
                .setId(news.getId())
                .setTitle(news.getTitle())
                .build();
        return newsDtoTitle;
    }
    /**
     * creating a list proto dto(NewsDtoTitle) on the list news
     *
     * @param  news list
     * @return list proto dto
     */
    public List<NewsDtoTitle> toListNewsDtoTitle(Page<News> news){
        return news.stream().map(this::toDtoNewsTitle).collect(Collectors.toList());
    }
}

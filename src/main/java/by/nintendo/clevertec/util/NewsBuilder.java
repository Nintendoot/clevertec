package by.nintendo.clevertec.util;

import by.nintendo.clevertec.dto.CommentDto;
import by.nintendo.clevertec.dto.NewsDto;
import by.nintendo.clevertec.dto.newsListComment;
import by.nintendo.clevertec.model.Comment;
import by.nintendo.clevertec.model.News;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsBuilder {

    private final CommentBuilder commentBuilder;

    public NewsBuilder(CommentBuilder commentBuilder) {
        this.commentBuilder = commentBuilder;
    }

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

    public List<CommentDto> newsListComment(List<Comment> list) {
        return list.stream()
                .map(x -> commentBuilder.toCommentDto(x))
                .collect(Collectors.toList());
    }
}

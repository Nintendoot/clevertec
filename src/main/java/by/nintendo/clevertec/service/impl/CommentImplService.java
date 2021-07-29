package by.nintendo.clevertec.service.impl;

import by.nintendo.clevertec.dto.CommentDto;
import by.nintendo.clevertec.exception.CommentNotFoundException;
import by.nintendo.clevertec.exception.NewsNotFoundException;
import by.nintendo.clevertec.model.Comment;
import by.nintendo.clevertec.model.News;
import by.nintendo.clevertec.repository.CommentRepository;
import by.nintendo.clevertec.repository.NewsRepository;
import by.nintendo.clevertec.service.CommentService;
import by.nintendo.clevertec.util.CommentBuilder;
import by.nintendo.clevertec.util.converter.ProtoConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentImplService implements CommentService {
    private final CommentRepository commentRepository;
    private final ProtoConverter protoConverter;
    private final CommentBuilder commentBuilder;
    private final NewsRepository newsRepository;

    public CommentImplService(CommentRepository commentRepository,
                              ProtoConverter protoConverter,
                              CommentBuilder commentBuilder, NewsRepository newsRepository) {
        this.commentRepository = commentRepository;
        this.protoConverter = protoConverter;
        this.commentBuilder = commentBuilder;
        this.newsRepository = newsRepository;
    }

    @Override
    public void create(Comment comment) throws RuntimeException {
        comment.setDate(LocalDate.now());
        Optional<News> news = newsRepository.findById(comment.getId_news());
        if (news.isPresent()) {
            commentRepository.save(comment);
        } else {
            throw new NewsNotFoundException(String.format("News with id: %s not found.", comment.getId_news()));
        }
    }

    @Override
    public void update(Long idComment, Comment com) throws RuntimeException {
        Optional<Comment> comment = commentRepository.findById(idComment);
        if (comment.isPresent()) {
            comment.get().setDate(LocalDate.now());
            comment.get().setText(com.getText());
            comment.get().setId_news(com.getId_news());
            comment.get().setUsername(com.getUsername());
            commentRepository.save(comment.get());
        } else {
            throw new CommentNotFoundException(String.format("Comment with id: %s not found.", idComment));
        }
    }

    @Override
    public String getAll(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);

        List<CommentDto> collect = comments.stream()
                .map(commentBuilder::toCommentDto)
                .collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder();
        for (CommentDto commentDto : collect) {
            stringBuilder.append(protoConverter.objectToJson(commentDto));
        }
        return stringBuilder.toString();
    }

    @Override
    public String getById(Long id) throws RuntimeException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return protoConverter.objectToJson(commentBuilder.toCommentDto(comment.get()));
        } else {
            throw new CommentNotFoundException(String.format("Comment with id: %s not found.", id));
        }
    }

    @Override
    public void deleteById(Long id) throws RuntimeException {
        Optional<Comment> byId = commentRepository.findById(id);
        if (byId.isPresent()) {
            commentRepository.deleteById(id);
        } else {
            throw new CommentNotFoundException(String.format("Comment with id: %s not found.", id));
        }
    }

    @Override
    public String search(String keyword) {
        List<Comment> all = commentRepository.findAll(keyword);
        if (!all.isEmpty()) {
            List<CommentDto> commentDtos = commentBuilder.toListCommentDto(all);
            StringBuilder stringBuilder = new StringBuilder();
            for (CommentDto newsDto : commentDtos) {
                stringBuilder.append(protoConverter.objectToJson(newsDto));
            }
            return stringBuilder.toString();
        } else {
            return "По вашим параметрам ничего не найдено.";
        }
    }
}

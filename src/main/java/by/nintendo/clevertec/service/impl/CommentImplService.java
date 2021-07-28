package by.nintendo.clevertec.service.impl;

import by.nintendo.clevertec.dto.CommentDto;
import by.nintendo.clevertec.exception.CommentNotFoundException;
import by.nintendo.clevertec.model.Comment;
import by.nintendo.clevertec.repository.CommentRepository;
import by.nintendo.clevertec.service.CommentService;
import by.nintendo.clevertec.util.CommentBuilder;
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
public class CommentImplService implements CommentService {
    private final CommentRepository commentRepository;
    private final ProtoConverter protoConverter;
    private final CommentBuilder commentBuilder;

    public CommentImplService(CommentRepository commentRepository, ProtoConverter protoConverter, CommentBuilder commentBuilder) {
        this.commentRepository = commentRepository;
        this.protoConverter = protoConverter;
        this.commentBuilder = commentBuilder;
    }

    @Override
    public void create(Comment comment) {
        comment.setDate(LocalDate.now());
        commentRepository.save(comment);
        log.info("In create - comment: {}", comment);
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
            log.info("In update - comment: {} update by id: {}", comment, idComment);
        } else {
            throw new CommentNotFoundException(String.format("Comment with id: %s not found.", idComment));
        }
    }

    @Override
    public String getAll(Pageable pageable) {
        Page<Comment> comments= commentRepository.findAll(pageable);
        List<CommentDto> collect = comments.stream().map(commentBuilder::toCommentDto).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (CommentDto commentDto : collect) {
            stringBuilder.append(protoConverter.objectToJson(commentDto));
        }
        log.info("In getAll - pageNumber: {} pageSize: {}", pageable.getPageNumber(), pageable.getPageSize());
        return stringBuilder.toString();
    }

    @Override
    public String getById(Long id) throws RuntimeException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            log.info("In getById - id:{} comment found: {}", id, comment);
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
            log.info("In deleteById - id: {}", id);
        } else {
            throw new CommentNotFoundException(String.format("Comment with id: %s not found.", id));
        }
    }
}

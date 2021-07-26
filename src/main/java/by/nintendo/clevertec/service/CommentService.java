package by.nintendo.clevertec.service;

import by.nintendo.clevertec.model.Comment;

import java.util.List;

public interface CommentService {
    void create(Comment comment);
    void update(Comment comment);

    List<Comment> getAll();

    Comment  getById(Long id);

    void deleteById(Long id);
}

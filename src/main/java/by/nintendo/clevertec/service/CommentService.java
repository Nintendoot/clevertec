package by.nintendo.clevertec.service;

import by.nintendo.clevertec.model.Comment;
import org.springframework.data.domain.Pageable;


public interface CommentService {
    void create(Comment comment);

    void update(Long idComment,Comment comment);

    String getAll(Pageable pageable);

    String  getById(Long id);

    void deleteById(Long id);
}

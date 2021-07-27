package by.nintendo.clevertec.service.impl;

import by.nintendo.clevertec.model.Comment;
import by.nintendo.clevertec.repository.CommentRepository;
import by.nintendo.clevertec.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentImplService implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void createOrUpdate(Comment comment) {
        comment.setDate(LocalDate.now());
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAll() {
        return null;
    }

    @Override
    public Comment getById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);

    }
}

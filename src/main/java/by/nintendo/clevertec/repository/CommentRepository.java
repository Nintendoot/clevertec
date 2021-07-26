package by.nintendo.clevertec.repository;

import by.nintendo.clevertec.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}

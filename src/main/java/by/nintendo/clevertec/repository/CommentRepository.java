package by.nintendo.clevertec.repository;

import by.nintendo.clevertec.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT  p FROM Comment p WHERE p.text LIKE %?1%" + "OR p.username LIKE %?1%" + " OR CONCAT(p.date, '') LIKE %?1%")
    List<Comment> findAll(String keyword);

    Page<Comment> findAll(Pageable pageable);
}

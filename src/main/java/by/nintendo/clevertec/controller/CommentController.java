package by.nintendo.clevertec.controller;

import by.nintendo.clevertec.model.Comment;
import by.nintendo.clevertec.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/comments")
public class CommentController {
    @Autowired
    @Qualifier("commentImplService")
    private CommentService commentService;

    @PostMapping
    public void createOrUpdateComments(@RequestBody Comment comment) {
        commentService.createOrUpdate(comment);
    }


    @DeleteMapping(value = "/{id}")
    public void deleteCommentById(@PathVariable Long id) {
        commentService.deleteById(id);
    }


}

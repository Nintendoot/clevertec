package by.nintendo.clevertec.controller;

import by.nintendo.clevertec.model.Comment;
import by.nintendo.clevertec.model.response.Status;
import by.nintendo.clevertec.service.CommentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(@Qualifier("commentImplService") CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Comment comment, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Status.NOT_CREATED.getName()).append(":");
            for (FieldError fieldError : result.getFieldErrors()) {
                stringBuilder.append(fieldError.getField()).append("-").append(fieldError.getDefaultMessage()).append(".");
            }
            return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.BAD_REQUEST);
        } else {
            commentService.create(comment);
            return new ResponseEntity<>(Status.CREATED, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @Valid @RequestBody Comment comment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Status.BAD.getName()).append(":");
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getField()).append("-").append(fieldError.getDefaultMessage()).append(".");
            }
            return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.BAD_REQUEST);
        } else {
            commentService.update(id, comment);
            return new ResponseEntity<>(Status.UPDATE, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Long id) {
        commentService.deleteById(id);
        return new ResponseEntity<>(Status.DELETE, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getCommentById(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllComment(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(commentService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> searchCommentFullText(@Param("keyword") String keyword) {
        return new ResponseEntity<>(commentService.search(keyword), HttpStatus.OK);
    }
}

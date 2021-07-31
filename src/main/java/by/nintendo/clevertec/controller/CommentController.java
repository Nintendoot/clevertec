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

/**
 * Controller that processes requests related to comments
 */

@RestController
@RequestMapping(path = "/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(@Qualifier("commentImplService") CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Add a new comment
     *
     * @param comment request body JSON
     * @param result  validation
     * @return the action performed, Status Response and HTTP status
     */
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


    /**
     * Updates the comment
     *
     * @param comment       request body JSON
     * @param id            comment from the request
     * @param bindingResult validation
     * @return the action performed, Status Response and HTTP status
     */

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


    /**
     * Deleted Comment
     *
     * @param id comment
     * @return the action performed, Status Response and HTTP status
     */

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Long id) {
        commentService.deleteById(id);
        return new ResponseEntity<>(Status.DELETE, HttpStatus.OK);
    }

    /**
     * Get Comment by id
     *
     * @param id comment
     * @return json Comment by id and HTTP status
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getById(id), HttpStatus.OK);
    }

    /**
     * Allows you to get a list of comments with a paginated view.
     *
     * @param pageable from request
     * @return json all comment with pagination and HTTP status
     */
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllComment(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(commentService.getAll(pageable), HttpStatus.OK);
    }

    /**
     * Search comment
     *
     * @param keyword search criteria
     * @return json found comment and HTTP status
     */
    @GetMapping(value = "/search")
    public ResponseEntity<?> searchCommentFullText(@Param("keyword") String keyword) {
        return new ResponseEntity<>(commentService.search(keyword), HttpStatus.OK);
    }
}

package by.nintendo.clevertec.controller;

import by.nintendo.clevertec.model.response.Status;
import by.nintendo.clevertec.model.News;
import by.nintendo.clevertec.service.NewsService;
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
 * Controller that processes requests related to news
 */

@RestController
@RequestMapping(path = "/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(@Qualifier("newsImplService") NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * Add a new news
     *
     * @param news request body JSON
     * @param bindingResult  validation
     * @return the action performed, Status Response and HTTP status
     */

    @PostMapping
    public ResponseEntity<?> createNews(@Valid @RequestBody News news, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Status.NOT_CREATED.getName()).append(":");
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getField()).append("-").append(fieldError.getDefaultMessage()).append(".");
            }
            return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.BAD_REQUEST);
        } else {
            newsService.create(news);
            return new ResponseEntity<>(Status.CREATED, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates the news
     *
     * @param news       request body JSON
     * @param id           news from the request
     * @param bindingResult validation
     * @return the action performed, Status Response and HTTP status
     */

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateNews(@PathVariable Long id, @Valid @RequestBody News news, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Status.BAD.getName()).append(":");
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getField()).append("-").append(fieldError.getDefaultMessage()).append(".");
            }
            return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.BAD_REQUEST);
        } else {
            newsService.update(id, news);
            return new ResponseEntity<>(Status.UPDATE, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deleted News
     *
     * @param id news
     * @return the action performed, Status Response and HTTP status
     */

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteNewsById(@PathVariable Long id) {
        newsService.deleteById(id);
        return new ResponseEntity<>(Status.DELETE, HttpStatus.OK);
    }

    /**
     * Allows you to get a list of news with a paginated view.
     *
     * @param pageable from request
     * @return json all news with pagination and HTTP status
     */

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllNews(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(newsService.getAll(pageable), HttpStatus.OK);
    }

    /**
     * Get news by id with a paginated view.
     *
     * @param id news
     * @param pageable from request
     * @return json News by id and HTTP status
     */

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getNewsById(@PathVariable Long id, @PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(newsService.getById(id, pageable), HttpStatus.OK);
    }

    /**
     * Search News
     *
     * @param keyword search criteria
     * @return json found news and HTTP status
     */

    @GetMapping(value = "/search")
    public ResponseEntity<?> searchNewsFullText(@Param("keyword") String keyword) {
        return new ResponseEntity<>(newsService.search(keyword), HttpStatus.OK);
    }


    /**
     * Get list of news with a paginated view.
     *
     * @param pageable from request
     * @return json News by id and HTTP status
     */

    @GetMapping
    public ResponseEntity<?> getAllNewsByTitle(@PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(newsService.getAllByTitle(pageable), HttpStatus.OK);
    }
}

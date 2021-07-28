package by.nintendo.clevertec.controller;

import by.nintendo.clevertec.model.response.Status;
import by.nintendo.clevertec.model.News;
import by.nintendo.clevertec.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(@Qualifier("newsImplService") NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    public ResponseEntity<?> createNews(@Valid @RequestBody News news, BindingResult bindingResult) {
        log.info("POST request /news");
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Status.NOT_CREATED.getName()).append(":");
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getField()).append("-").append(fieldError.getDefaultMessage()).append(".");
            }
            log.warn("POST request /news hasErrors: {}",stringBuilder.toString());
            return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.BAD_REQUEST);
        } else {
            newsService.create(news);
            log.info("POST request /news response status: {}",Status.CREATED);
            return new ResponseEntity<>(Status.CREATED, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateNews(@PathVariable Long id, @Valid @RequestBody News news, BindingResult bindingResult) {
        log.info("PUT request /news/{}",id);
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Status.BAD.getName()).append(":");
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getField()).append("-").append(fieldError.getDefaultMessage()).append(".");
            }
            log.warn("POST request /news/{} hasErrors: {}",id,stringBuilder.toString());
            return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.BAD_REQUEST);
        } else {
            newsService.update(id, news);
            log.info("POST request /news/{} response status: {}",id,Status.CREATED);
            return new ResponseEntity<>(Status.UPDATE, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteNewsById(@PathVariable Long id) {
        log.info("DELETE request /news/{}",id);
        newsService.deleteById(id);
        log.info("DELETE request /news/{} response status: {}",id,Status.DELETE);
        return new ResponseEntity<>(Status.DELETE,HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllNews(@PageableDefault(size = 5,direction = Sort.Direction.DESC)Pageable pageable) {
        return new ResponseEntity<>(newsService.getAll(pageable), HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getNewsById(@PathVariable Long id,@PageableDefault(size = 10,direction = Sort.Direction.DESC)Pageable pageable) {
        return new ResponseEntity<>(newsService.getById(id,pageable), HttpStatus.OK);
    }
}

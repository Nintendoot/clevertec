package by.nintendo.clevertec.controller;

import by.nintendo.clevertec.model.News;
import by.nintendo.clevertec.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/news")
public class NewsController {
    @Autowired
    @Qualifier("newsImplService")
    private NewsService newsService;

    @PostMapping
    public ResponseEntity<Object> createOrUpdateNews(@RequestBody News newsDto) {


        return new ResponseEntity<>(newsService.createOrUpdate(newsDto), HttpStatus.CREATED);

    }

    @DeleteMapping(value = "/{id}")
    public void deleteNewsById(@PathVariable Long id) {
        newsService.deleteById(id);
    }


    @GetMapping(value = "/all")
    public ResponseEntity<Object> getAllNews() {

        return new ResponseEntity<>(newsService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getNewsById(@PathVariable Long id) {

        return new ResponseEntity<>(newsService.getById(id), HttpStatus.OK);
    }
}

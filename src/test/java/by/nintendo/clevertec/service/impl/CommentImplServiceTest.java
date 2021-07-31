package by.nintendo.clevertec.service.impl;

import by.nintendo.clevertec.exception.NewsNotFoundException;
import by.nintendo.clevertec.model.Comment;
import by.nintendo.clevertec.repository.CommentRepository;
import by.nintendo.clevertec.util.CommentBuilder;
import by.nintendo.clevertec.util.converter.ProtoConverter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class CommentImplServiceTest {

    @Autowired
    private CommentImplService commentService;

   @MockBean
    private CommentRepository commentRepository;
    @Mock
    private CommentBuilder commentBuilder;

   @Mock
    private ProtoConverter protoConverter;


    @Test
    void testCreate() {
        Comment comment=new Comment();
        comment.setUsername("Test");
        comment.setText("Test text");
        comment.setId_news(1L);
        comment.setDate(LocalDate.now());
        commentService.create(comment);
        verify(commentRepository, times(1)).save(comment);
        comment.setId_news(300L);
        assertThrows(NewsNotFoundException.class, () -> commentService.create(comment));
    }


    @Test
    void testGetAll() {
//      Pageable p=new P
//        Page<Comment> list=new ArrayList<>();
//        when(commentRepository.findAll()).thenReturn(list);


    }

    @Test
    void testGetById() {
    }

    @Test
    void testDeleteById() {
    }

    @Test
    void testSearch() {
    }
}
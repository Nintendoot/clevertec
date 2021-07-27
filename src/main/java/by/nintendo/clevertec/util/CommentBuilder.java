package by.nintendo.clevertec.util;

import by.nintendo.clevertec.dto.CommentDto;
import by.nintendo.clevertec.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentBuilder {

    public CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = CommentDto.newBuilder()
                .setDate(comment.getDate().toString())
                .setId(comment.getId())
                .setText(comment.getText())
                .setUsername(comment.getUsername())
                .setId(comment.getId()).build();
        return commentDto;
    }
}

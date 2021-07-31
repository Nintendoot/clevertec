package by.nintendo.clevertec.util;

import by.nintendo.clevertec.dto.CommentDto;
import by.nintendo.clevertec.model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Converter Comment to Proto classes
 */
@Component
public class CommentBuilder {
    /**
     * creating a proto dto on the comment
     *
     * @param comment object
     * @return proto dto(CommentDto)
     */
    public CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = CommentDto.newBuilder()
                .setDate(comment.getDate().toString())
                .setId(comment.getId())
                .setText(comment.getText())
                .setUsername(comment.getUsername())
                .setId(comment.getId()).build();
        return commentDto;
    }
    /**
     * creating a list proto dto on the comment list
     *
     * @param comments list object
     * @return list proto dto
     */
    public List<CommentDto> toListCommentDto(List<Comment> comments) {

        return comments.stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());
    }
}

package by.nintendo.clevertec.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {
    @NotEmpty(message = "The fieldname is empty.")
    @Pattern(regexp = "[A-Za-z]{4,10}", message = "name : должен быть больше 4 и меньше 10 и содержать только латинские символы.")
    @Column(name = "username")
    private String username;
    @NotNull
    @Column(name = "id_news")
    private Long id_news;
}

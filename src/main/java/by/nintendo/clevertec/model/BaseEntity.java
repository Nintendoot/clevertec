package by.nintendo.clevertec.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "text")
    private String text;
}

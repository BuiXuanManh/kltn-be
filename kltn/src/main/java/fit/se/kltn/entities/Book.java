package fit.se.kltn.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fit.se.kltn.dto.BookComputed;
import fit.se.kltn.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private String id;
    @Indexed
    private String title;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateDate;
    @Indexed
    private String image;
    @Indexed
    private String bgImage;
    private int pageCount;
    @ToString.Include
    private String shortDescription;
    @ToString.Include
    private String longDescription;
    private BookStatus status;
    @ToString.Include
    private List<Author> authors;
    @ToString.Include
    private List<Genre> genres;
    private BookComputed bookComputed;
}

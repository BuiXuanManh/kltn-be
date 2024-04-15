package fit.se.kltn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @Indexed
    private String id;
    private String isbn;
    @Indexed
    private String title;
    private int pageCount;
    @CreatedDate
    private LocalDate createdAt;
    @LastModifiedDate
    private LocalDate uploadDate;
    private String image;
    private String shortDescription;
    private String longDescription;
    private String status;
    private List<String> authors;
    private List<String> genres;
    private List<String> categories;
    private String content;
    private List<PageBook> pages;
}

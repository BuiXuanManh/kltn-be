package fit.se.kltn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private String id;
    private String isbn;
    private String title;
    private int pageCount;
    private LocalDate publishedDate;
    private LocalDate uploadDate;
    private String thumbnailUrl;
    private String shortDescription;
    private String longDescription;
    private int unit;
    private String location;
    private String status;
    private List<String> authors;
    private List<String> genres;
    private List<String> categories;
}

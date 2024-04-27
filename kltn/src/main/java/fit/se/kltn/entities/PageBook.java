package fit.se.kltn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("pages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBook {
    @Id
    private String id;
    private String name;
    @Indexed
    private int pageNo;
    private String content;
    @DocumentReference(lazy = true)
    @Field("book_id")
    private Book book;

    public PageBook(String name, int pageNo, String content, Book book) {
        this.name = name;
        this.pageNo = pageNo;
        this.content = content;
        this.book = book;
    }
}

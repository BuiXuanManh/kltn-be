package fit.se.kltn.controller;

import fit.se.kltn.entities.Book;
import fit.se.kltn.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Qualifier("bookImpl")
    @Autowired
    private BookService service;
    @GetMapping
    public List<Book> getAll(){
        return service.findAll();
    }
    @PostMapping
    public Book save(@RequestBody Book book){
        return service.save(book);
    }
}

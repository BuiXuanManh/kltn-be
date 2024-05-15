package fit.se.kltn.controller;

import fit.se.kltn.dto.NominatedBookDto;
import fit.se.kltn.entities.Book;
import fit.se.kltn.entities.NominatedBook;
import fit.se.kltn.services.BookInteractionService;
import fit.se.kltn.services.BookService;
import fit.se.kltn.services.NominatedBookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nominatedBook")
@Slf4j
public class NominatedBookController {
    @Qualifier("nominatedBookImpl")
    @Autowired
    private NominatedBookService service;
    @Autowired
    private BookService bookService;
    @Qualifier("bookInteractionImpl")
    @Autowired
    private BookInteractionService interactionService;
//    public NominatedBook save(Book book,int nominatedCount) {
//        NominatedBook nominatedBook = new NominatedBook();
//        nominatedBook.setBook(book);
//        nominatedBook.set
//        nominatedBook.setUpdateAt(LocalDateTime.now());
//        return service.save(nominatedBook);
//    }
    @GetMapping("/save")
    @Operation(summary = "save nominated books")
    public List<NominatedBookDto> getNominatedBook(){
        return interactionService.findNominationsListWithCounts();
    }
}

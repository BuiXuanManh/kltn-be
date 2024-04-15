package fit.se.kltn.controller;

import fit.se.kltn.dto.BookPageDto;
import fit.se.kltn.entities.Book;
import fit.se.kltn.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/books")
@Slf4j
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
    @GetMapping("/getAll")
    public BookPageDto getAllBySort(@RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size){
        int currentPage= page.orElse(1);
        int currentSize=size.orElse(10);
        Page<Book> p=service.findPage(currentPage-1, currentSize,"uploadDate","asc");
        int totalPage= p.getTotalPages();
        if(totalPage>0){
            List<Integer> pageNumbers= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
//            PAGE=currentPage;
//            SIZE=currentSize;
            BookPageDto dto = new BookPageDto(p,pageNumbers);
            log.info(dto+"");
            return dto;
        }
        throw new RuntimeException("Invalid page");
    }
}

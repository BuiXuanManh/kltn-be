package fit.se.kltn.controller;

import fit.se.kltn.dto.BookPageDto;
import fit.se.kltn.entities.Book;
import fit.se.kltn.entities.PageBook;
import fit.se.kltn.services.BookService;
import fit.se.kltn.services.PageService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Autowired
    private PageService pageService;

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả sách")
    public List<Book> getAll() {
        return service.findAll();
    }

    @PostMapping
    @Operation(summary = "Thêm sách")
    public Book save(@RequestBody Book book) {
        return service.save(book);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Lấy danh sách theo trang",description = "vó thể truyền vào số trang và kích thước trang hoặc không")
    public BookPageDto getAllBySort(@RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int currentSize = size.orElse(10);
        Page<Book> p = service.findPage(currentPage - 1, currentSize, "uploadDate", "asc");
        int totalPage = p.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
//            PAGE=currentPage;
//            SIZE=currentSize;
            BookPageDto dto = new BookPageDto(p, pageNumbers);
            return dto;
        }
        throw new RuntimeException("Invalid page");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Tìm sách theo id")
    public Book findById(@PathVariable String id) {
        Optional<Book> b = service.findById(id);
        if (b.isPresent()) return b.get();
        throw new RuntimeException("không tìm thấy sách có id " + id);
    }

    @PostMapping("/{bookId}")
    @Operation(summary = "Thêm trang cho sách theo id")
    public PageBook savePage(@RequestBody PageBook pageBook, @PathVariable("bookId") String id) {
        Optional<Book> b = service.findById(id);
        if (b.isEmpty()) {
            throw new RuntimeException("không tìm thấy book có id " + id);
        }
        Book bb = b.get();
        List<PageBook> pages = bb.getPages();
        if(pageBook!=null){
            pageService.save(pageBook);
            pages.add(pageBook);
        }
        bb.setPages(pages);
        service.save(bb);
        return pageBook;
    }
    @GetMapping("/pages/{id}/{pageNo}")
    @Operation(summary = "Tìm trang theo book Id và số trang")
    public PageBook findByPageNo(@PathVariable("pageNo") int pageNo, @PathVariable("id") String id){
        Optional<Book> b = service.findById(id);
        if(b.isEmpty()){
            throw new RuntimeException("không tìm thấy book có id: "+id);
        }
        Book bp = b.get();
        List<PageBook> pages= bp.getPages();
        if(pageNo<=0|| pageNo>pages.size()){
            throw new RuntimeException("Không tìm thấy số trang: "+pageNo);
        }
        for(PageBook pageBook : pages){
            if(pageBook.getPageNo()==pageNo){
                return pageBook;
            }
        }
        throw new RuntimeException("Error");
    }
}

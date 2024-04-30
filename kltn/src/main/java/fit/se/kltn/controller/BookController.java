package fit.se.kltn.controller;

import fit.se.kltn.dto.BookPageDto;
import fit.se.kltn.dto.UserDto;
import fit.se.kltn.entities.*;
import fit.se.kltn.enums.RateType;
import fit.se.kltn.exception.NotFoundException;
import fit.se.kltn.services.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Qualifier("bookInteractionImpl")
    @Autowired
    private BookInteractionService interactionService;
    @Autowired
    private PageInteractionService pageInteractionService;
    @Autowired
    private ProfileService profileService;
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;
    @Autowired
    private RateBookService rateBookService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/rateBook/getAll/{id}")
    @Operation(summary = "lấy tất cả đánh giá sách theo book id")
    public List<RateBook> findByBookId(@PathVariable("id") String id){
        return rateBookService.findByBookId(id);
    }
    @GetMapping("/rateBook/{id}")
    @Operation(summary = "tìm đánh theo book id và profile id")
    public RateBook findRateBookByProfileIdAndBookId(@AuthenticationPrincipal UserDto dto, @PathVariable("id") String id) {
        Profile p = authenProfile(dto);
        Book page = service.findById(id).orElseThrow(() -> new NotFoundException("khÔng tìm thấy book có id: " + id));
        RateBook rate = rateBookService.findByProfileIdAndBookId(p.getId(), page.getId()).orElseThrow(() -> new NotFoundException("không tìm thấy đánh giá sách"));
        return rate;
    }

    public Profile authenProfile(UserDto dto) {
        User u = userService.findByUserName(dto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        Optional<Profile> p = profileService.findByUserId(u.getId());
        if (p.isPresent()) {
            return p.get();
        } else throw new RuntimeException("không tìm thấy profile user có mssv: " + u.getMssv());
    }

    @GetMapping("/interactions")
    @Operation(summary = "lấy danh sách tương tác theo profile id")
    public List<BookInteraction> getBookInteraction(@AuthenticationPrincipal UserDto dto) {
        Profile p = authenProfile(dto);
        return interactionService.findByProfileId(p.getId());
    }

    @PostMapping("/interactions/read/{bookId}/{pageId}/{page}")
    @Operation(summary = "Cập nhập sách đang đọc của user")
    public List<BookInteraction> updateBookInteraction(@AuthenticationPrincipal UserDto dto, @PathVariable("bookId") String bookId, @PathVariable("pageId") String pageId, @PathVariable("page") int page) {
        Profile p = authenProfile(dto);
        Book b = service.findById(bookId).orElseThrow(() -> new NotFoundException("không tìm thấy book có id: " + bookId));
        Optional<BookInteraction> find = interactionService.getBookInteraction(b.getId(), p.getId());
        PageBook pageFind = pageService.findById(pageId).orElseThrow(() -> new NotFoundException("không timg thấy page có id là: " + pageId));
        if (find.isPresent()) {
            BookInteraction f = find.get();
            f.setReadCount(page);
            BookInteraction save = interactionService.save(f);
            Optional<PageInteraction> pageInteraction = pageInteractionService.findByProfileIDAndPageBookId(p.getId(), pageId);
            if (pageInteraction.isPresent()) {
                PageInteraction op = pageInteraction.get();
                op.setRead(op.getRead() + 1);
                op.setReadTime(LocalDateTime.now());
                pageInteractionService.save(op);
                List<BookInteraction> list = interactionService.findByProfileId(p.getId());
                return list;
            }
            PageInteraction interaction = new PageInteraction();
            interaction.setProfile(p);
            interaction.setPageBook(pageFind);
            interaction.setReadTime(LocalDateTime.now());
            interaction.setRead(1);
            pageInteractionService.save(interaction);
            List<BookInteraction> list = interactionService.findByProfileId(p.getId());
            return list;
        }
        BookInteraction f = new BookInteraction();
        f.setBook(b);
        f.setProfile(p);
        f.setReadCount(page);
        BookInteraction save = interactionService.save(f);
        PageInteraction interaction = new PageInteraction();
        interaction.setProfile(p);
        interaction.setPageBook(pageFind);
        interaction.setReadTime(LocalDateTime.now());
        interaction.setRead(1);
        pageInteractionService.save(interaction);
        List<BookInteraction> list = interactionService.findByProfileId(p.getId());
        return list;
    }

    @PostMapping("/genres")
    @Operation(summary = "Tìm sách theo danh sách thể loại ")
    public BookPageDto getBookByGenreId(@RequestBody List<Genre> genres, @RequestParam(value = "page", required = false) Optional<Integer> pageNo, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        int currentPage = pageNo.orElse(1);
        int currentSize = size.orElse(10);
        Page<Book> page = getFilteredBooks(genres, currentPage, currentSize);
        int totalPage = page.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            BookPageDto dto = new BookPageDto(page, pageNumbers);
            return dto;
        }
        return null;
    }

    public Page<Book> getFilteredBooks(List<Genre> genres, int pageNo, int pageSize) {
        List<Book> books = service.findAll();
        List<Book> result = new ArrayList<>();

        // Lọc các cuốn sách theo thể loại
        for (Book book : books) {
            if (book.getGenres().stream().anyMatch(genre -> genres.stream().anyMatch(g -> g.getId().equals(genre.getId())))) {
                result.add(book);
            }
        }

        // Tạo trang sách từ danh sách đã lọc
        int start = Math.min((pageNo - 1) * pageSize, result.size());
        int end = Math.min(start + pageSize, result.size());
        List<Book> pageContent = result.subList(start, end);

        // Tạo đối tượng Page từ danh sách đã lọc
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        return new PageImpl<>(pageContent, pageRequest, result.size());
    }

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
    @Operation(summary = "Lấy danh sách theo trang", description = "vó thể truyền vào số trang và kích thước trang hoặc không")
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
        else throw new RuntimeException("không tìm thấy sách có id " + id);
    }

    @PostMapping("/{bookId}")
    @Operation(summary = "Thêm trang cho sách theo id")
    public PageBook savePage(@RequestBody PageBook pageBook, @PathVariable("bookId") String id) {
        Optional<Book> b = service.findById(id);
        if (b.isEmpty()) {
            throw new RuntimeException("không tìm thấy book có id " + id);
        }
        Book bb = b.get();
        if (pageBook != null) {
            pageBook.setBook(bb);
            pageService.save(pageBook);
        }
        return pageBook;
    }
}

package fit.se.kltn.controller;

import fit.se.kltn.dto.BookPageDto;
import fit.se.kltn.dto.CommentDto;
import fit.se.kltn.dto.RateDto;
import fit.se.kltn.dto.UserDto;
import fit.se.kltn.entities.*;
import fit.se.kltn.enums.BookStatus;
import fit.se.kltn.enums.ERole;
import fit.se.kltn.enums.InteractionStatus;
import fit.se.kltn.exception.NotFoundException;
import fit.se.kltn.repositoties.BookRepository;
import fit.se.kltn.services.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
    private AuthorService authorService;
    @Autowired
    private ComputedBookService computedBookService;

    //    @GetMapping("/saves")
//    @Operation(summary = "Saves")
//    public List<BookInteraction> saveslist() {
//        List<BookInteraction> l = interactionService.findAll();
//        for (BookInteraction book : l) {
//            book.setStatus(InteractionStatus.ACTIVE);
//            interactionService.save(book);
//        }
//        return interactionService.findAll();
//    }
    @PostMapping("/interaction/delete/{InteractionId}")
    public BookInteraction deleteInteraction(@PathVariable("InteractionId") String id, @AuthenticationPrincipal UserDto dto) {
        Profile p = authenProfile(dto);
        BookInteraction f = interactionService.findById(id).orElseThrow(() -> new NotFoundException("không tìm thấy tương tác có id: " + id));
        f.setStatus(InteractionStatus.DELETE);
        return interactionService.save(f);
    }

    @GetMapping("/nominate/total")
    public BookPageDto findByNominated(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "12") int size) {
        List<Book> l = interactionService.findRecentNominations("total");
        return converPage(l, page, size, "nominatetotal");
    }

    @GetMapping("/new/total")
    public BookPageDto findByNewBook(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "12") int size) {
        List<Book> l = service.findByCreatedAt();
        return converPage(l, page, size, "createdAt");
    }

    @GetMapping("/interactions/save")
    public List<BookInteraction> getInteractionBySave(@AuthenticationPrincipal UserDto dto) {
        Profile p = authenProfile(dto);
        List<BookInteraction> list = interactionService.findByProfileId(p.getId());
        Collections.reverse(list);
        return list;
    }

    public BookPageDto getPage(Integer page, Integer size, String sortBy, String field) {
        Page<Book> p = service.findPage(page - 1, size, sortBy, "desc");
        int totalPage = p.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            BookPageDto dto = new BookPageDto(p, pageNumbers, field);
            return dto;
        }
        throw new RuntimeException("Invalid page");
    }

    @GetMapping("/rate/recent")
    public Page<RateDto> getComments(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "8") int size) {
        List<ComputedBook> listcompu = computedBookService.getAll();
        List<Book> books = pageInteractionService.findRecentReads("total");
        List<RateDto> dtos = new ArrayList<>();
        books.forEach(book -> {
            listcompu.stream()
                    .filter(e -> e.getBook().getId().equals(book.getId()))
                    .findFirst()
                    .ifPresent(e -> {
                        RateDto dto = new RateDto();
                        dto.setBook(book);
                        dto.setRate(e.getTotalRate());
                        dtos.add(dto);
                    });
        });
        Pageable pageable = PageRequest.of(page - 1, size);
        int start = Math.min((int) pageable.getOffset(), dtos.size());
        int end = Math.min((start + pageable.getPageSize()), dtos.size());
        Page<RateDto> pageResult = new PageImpl<>(dtos.subList(start, end), pageable, dtos.size());
        return pageResult;
    }

    public BookPageDto converPage(List<Book> result, int pageNo, int pageSize, String field) {
        int start = Math.min((pageNo - 1) * pageSize, result.size());
        int end = Math.min(start + pageSize, result.size());
        List<Book> pageContent = result.subList(start, end);
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        Page<Book> page = new PageImpl<>(pageContent, pageRequest, result.size());
        int totalPage = page.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            BookPageDto dto = new BookPageDto(page, pageNumbers, field);
            return dto;
        }
        return null;
    }

    @GetMapping("/title/{title}")
    public BookPageDto find(@PathVariable("title") String title,
                            @RequestParam(value = "page", required = false) Optional<Integer> page,
                            @RequestParam(value = "size", required = false) Optional<Integer> size
    ) {
        List<Book> list = service.findAll();
        List<Book> l = list.stream().filter(b ->
                b.getTitle().toLowerCase().contains(title.toLowerCase())
        ).collect(Collectors.toList());
        int currentPage = page.orElse(1);
        int currentSize = size.orElse(10);
        return converPage(l, currentPage, currentSize, "title");
    }

    @PostMapping("/save")
    public Book save(@RequestBody Book book, @RequestParam("author") String author, @AuthenticationPrincipal UserDto dto) {
        authenProfile(dto);

        if (!dto.getRole().equals(ERole.ADMIN)) {
            throw new RuntimeException("bạn không phải admin");
        }

        Optional<Book> existingBookOpt = service.findByTitle(book.getTitle().trim());
        LocalDateTime now = LocalDateTime.now();

        if (existingBookOpt.isPresent()) {
            Book existingBook = existingBookOpt.get();
            boolean hasDuplicateAuthor = existingBook.getAuthors().stream()
                    .anyMatch(existingAuthor -> existingAuthor.getName().equals(author));

            if (author != null) {
                Optional<Author> authorOpt = authorService.findByName(author.trim());
                if (authorOpt.isPresent()) {
                    existingBook.setAuthors(List.of(authorOpt.get()));
                } else {
                    Author newAuthor = new Author();
                    newAuthor.setName(author);
                    Author savedAuthor = authorService.save(newAuthor);
                    existingBook.setAuthors(List.of(savedAuthor));
                }
            }

            if (hasDuplicateAuthor) {
                existingBook.setGenres(book.getGenres());
                existingBook.setLongDescription(book.getLongDescription());
                existingBook.setShortDescription(book.getShortDescription());
                existingBook.setUpdateDate(now);
                existingBook.setBgImage(book.getBgImage());
                existingBook.setImage(book.getImage());
                existingBook.setStatus(BookStatus.ACTIVE);
                return service.save(existingBook);
            } else {
                if (author != null) {
                    Optional<Author> authorOpt = authorService.findByName(author.trim());
                    if (authorOpt.isPresent()) {
                        book.setAuthors(List.of(authorOpt.get()));
                    } else {
                        Author newAuthor = new Author();
                        newAuthor.setName(author);
                        Author savedAuthor = authorService.save(newAuthor);
                        book.setAuthors(List.of(savedAuthor));
                    }
                }
                book.setUpdateDate(now);
                book.setCreatedAt(now);
                return service.save(book);
            }
        }

        if (author != null) {
            Optional<Author> authorOpt = authorService.findByName(author.trim());
            if (authorOpt.isPresent()) {
                book.setAuthors(List.of(authorOpt.get()));
            } else {
                Author newAuthor = new Author();
                newAuthor.setName(author);
                Author savedAuthor = authorService.save(newAuthor);
                book.setAuthors(List.of(savedAuthor));
            }
        }

        book.setUpdateDate(now);
        book.setCreatedAt(now);
        return service.save(book);
    }


    @GetMapping("/getAll")
    @Operation(summary = "Lấy danh sách theo trang", description = "có thể truyền vào số trang và kích thước trang hoặc không")
    public BookPageDto getAllBySort(@RequestParam("field") String field, @RequestParam(value = "page", required = false) Optional<Integer> page, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int currentSize = size.orElse(10);
        return getPage(currentPage, currentSize, field, field);
    }

    @GetMapping("/interaction/{id}")
    public BookInteraction getInteraction(@PathVariable("id") String id, @AuthenticationPrincipal UserDto dto) {
        Book b = service.findById(id).orElseThrow(() -> new NotFoundException("không tìm thấy sách id: " + id));
        Profile p = authenProfile(dto);
        Optional<BookInteraction> inte = interactionService.getBookInteraction(b.getId(), p.getId());
        return inte.orElse(null);
    }

    @PostMapping("/follow/{bookId}")
    public BookInteraction followBook(@PathVariable("bookId") String id, @AuthenticationPrincipal UserDto dto) {
        Book b = service.findById(id).orElseThrow(() -> new NotFoundException("không tìm thấy sách id: " + id));
        Profile p = authenProfile(dto);
        Optional<BookInteraction> inte = interactionService.getBookInteraction(b.getId(), p.getId());
        if (inte.isEmpty()) {
            BookInteraction interaction = new BookInteraction();
            interaction.setBook(b);
            interaction.setProfile(p);
            interaction.setFollowed(true);
            return interactionService.save(interaction);
        }
        BookInteraction interaction = inte.get();
        interaction.setFollowed(true);
        return interactionService.save(interaction);
    }

    @PostMapping("/follow/cancel/{bookId}")
    public BookInteraction followCancelBook(@PathVariable("bookId") String id, @AuthenticationPrincipal UserDto dto) {
        Book b = service.findById(id).orElseThrow(() -> new NotFoundException("không tìm thấy sách id: " + id));
        Profile p = authenProfile(dto);
        Optional<BookInteraction> inte = interactionService.getBookInteraction(b.getId(), p.getId());
        if (inte.isEmpty()) {
            BookInteraction interaction = new BookInteraction();
            interaction.setBook(b);
            interaction.setProfile(p);
            interaction.setFollowed(false);
            return interactionService.save(interaction);
        }
        BookInteraction interaction = inte.get();
        interaction.setFollowed(false);
        return interactionService.save(interaction);
    }

    @PostMapping("/nominate/{bookId}")
    public BookInteraction nomitateBook(@PathVariable("bookId") String id, @AuthenticationPrincipal UserDto dto) {
        Book b = service.findById(id).orElseThrow(() -> new NotFoundException("không tìm thấy sách id: " + id));
        Profile p = authenProfile(dto);
        Optional<BookInteraction> inte = interactionService.getBookInteraction(b.getId(), p.getId());
        if (inte.isEmpty()) {
            BookInteraction interaction = new BookInteraction();
            interaction.setBook(b);
            interaction.setProfile(p);
            interaction.setNominated(true);
            interaction.setNominalTime(LocalDateTime.now());
            return interactionService.save(interaction);
        }
        BookInteraction interaction = inte.get();
        interaction.setNominated(true);
        interaction.setNominalTime(LocalDateTime.now());
        return interactionService.save(interaction);
    }

    @PostMapping("/nominate/cancel/{bookId}")
    public BookInteraction nomitateCancelBook(@PathVariable("bookId") String id, @AuthenticationPrincipal UserDto dto) {
        Book b = service.findById(id).orElseThrow(() -> new NotFoundException("không tìm thấy sách id: " + id));
        Profile p = authenProfile(dto);
        Optional<BookInteraction> inte = interactionService.getBookInteraction(b.getId(), p.getId());
        if (inte.isEmpty()) {
            BookInteraction interaction = new BookInteraction();
            interaction.setBook(b);
            interaction.setProfile(p);
            interaction.setNominated(false);
            interaction.setNominalTime(LocalDateTime.now());
            return interactionService.save(interaction);
        }
        BookInteraction interaction = inte.get();
        interaction.setNominated(false);
        interaction.setNominalTime(LocalDateTime.now());
        return interactionService.save(interaction);
    }

    @GetMapping("/get/new")
    public BookPageDto getBookPage(@RequestParam(value = "page", required = false) Optional<Integer> page,
                                   @RequestParam(value = "size", required = false) Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int currentSize = size.orElse(10);
        return getPage(currentPage, currentSize, "createdAt", "new");
    }

    @GetMapping("/find/keyword")
    @Operation(summary = "tìm theo tiêu đề, tên tác giả, thể loại")
    public BookPageDto findBook(@RequestParam("keyword") String keyword,
                                @RequestParam(value = "page", required = false) Optional<Integer> page,
                                @RequestParam(value = "size", required = false) Optional<Integer> size) {
        List<Book> list = service.findByCreatedAt();
        List<Book> l = list.stream().filter(b -> {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
            if (b.getAuthors().stream().anyMatch(author ->
                    author.getName().toLowerCase().contains(keyword.toLowerCase()))) {
                return true;
            }
            return b.getGenres().stream().anyMatch(genre ->
                    genre.getName().toLowerCase().contains(keyword.toLowerCase()));
        }).toList();
        int currentPage = page.orElse(1);
        int currentSize = size.orElse(10);
        return converPage(l, currentPage, currentSize, "keyword");
    }

    @GetMapping("/rateBook/getAll/{id}")
    @Operation(summary = "lấy tất cả đánh giá sách theo book id")
    public List<RateBook> findByBookId(@PathVariable("id") String id) {
        return rateBookService.findByBookId(id);
    }

    @GetMapping("/rateBook/{id}")
    @Operation(summary = "tìm đánh theo book id và profile id")
    public RateBook findRateBookByProfileIdAndBookId(@AuthenticationPrincipal UserDto dto, @PathVariable("id") String id) {
        Profile p = authenProfile(dto);
        Book page = service.findById(id).orElseThrow(() -> new NotFoundException("khÔng tìm thấy book có id: " + id));
        return rateBookService.findByProfileIdAndBookId(p.getId(), page.getId()).orElse(null);
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
        Book b = service.findById(bookId).orElseThrow(() -> new NotFoundException("không tìm thấy book có id: " + bookId));
        PageBook pageFind = pageService.findById(pageId).orElseThrow(() -> new NotFoundException("không timg thấy page có id là: " + pageId));
        Profile p = authenProfile(dto);
        Optional<BookInteraction> find = interactionService.getBookInteraction(b.getId(), p.getId());
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
        return getFilteredBooks(genres, currentPage, currentSize);
    }


    public BookPageDto getFilteredBooks(List<Genre> genres, int pageNo, int pageSize) {
        List<Book> books = service.findAll();
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenres().stream().anyMatch(genre -> genres.stream().anyMatch(g -> g.getId().equals(genre.getId())))) {
                result.add(book);
            }
        }
        return converPage(result, pageNo, pageSize, "genres");
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

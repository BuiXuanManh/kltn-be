package fit.se.kltn.controller;

import fit.se.kltn.dto.CommentDto;
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
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@Slf4j
public class CommentController {
    @Qualifier("commentImpl")
    @Autowired
    private CommentService service;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private BookService bookService;
    @Autowired
    private PageService pageService;
    @Autowired
    private UserService userService;
    @Autowired
    private RateBookService rateBookService;
    @Autowired
    private ComputedBookService computedBookService;

    @GetMapping("/rate/recent")
    public Page<CommentDto> getComments(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "7") int size) {
        List<ComputedBook> listcompu = computedBookService.getAll();
        List<Comment> comments = service.findByRecentAndType();
        List<CommentDto> dtos = new ArrayList<>();
        comments.forEach(comment -> {
            listcompu.stream()
                    .filter(e -> e.getBook().getId().equals(comment.getBook().getId()))
                    .findFirst()
                    .ifPresent(e -> {
                        CommentDto dto = new CommentDto();
                        dto.setComment(comment);
                        dto.setRate(e.getTotalRate());
                        dtos.add(dto);
                    });
        });
        Pageable pageable = PageRequest.of(page - 1, size);
        int start = Math.min((int) pageable.getOffset(), dtos.size());
        int end = Math.min((start + pageable.getPageSize()), dtos.size());
        Page<CommentDto> pageResult = new PageImpl<>(dtos.subList(start, end), pageable, dtos.size());
        return pageResult;
    }


    @GetMapping("/page/get/{pageId}")
    @Operation(summary = "lấy comment theo pageId và profile id")
    public Comment getComment(@AuthenticationPrincipal UserDto dto, @PathVariable("pageId") String pageId) {
        Profile p = authenProfile(dto);
        PageBook page = pageService.findById(pageId).orElseThrow(() -> new NotFoundException("khÔng tìm thấy page có id: " + pageId));
        return service.findByProfileIdAndPageIdAndType(p.getId(), page.getId(), RateType.COMMENT).orElseThrow(() -> new RuntimeException("không tìm thấy comment"));
    }

    @GetMapping
    @Operation(summary = "lấy danh sách tất cả comment")
    public List<Comment> getAll() {
        return service.findAll();
    }

    @PostMapping("/rateBook/{bookId}")
    @Operation(summary = "thêm đánh giá vào book")
    public Comment addRateBook(@AuthenticationPrincipal UserDto dto, @PathVariable("bookId") String id, @RequestBody @Valid RateBook rateBook) {
        Profile p = authenProfile(dto);
        Book book = bookService.findById(id).orElseThrow(() -> new NotFoundException("khÔng tìm thấy book có id: " + id));
        Optional<Comment> c = service.findByProfileIdAndBookIdAndType(p.getId(), id, RateType.RATE);
        Optional<RateBook> rate = rateBookService.findByProfileIdAndBookId(p.getId(), book.getId());
        double avg = (rateBook.getContentBook() + rateBook.getHelpful() + rateBook.getUnderstand()) / 3;
        if (rate.isEmpty()) {
            rateBook.setBook(book);
            rateBook.setProfile(p);
            rateBook.setTotalRate(Math.round(avg * 100) / 100.0);
            rateBookService.save(rateBook);
        } else {
            RateBook r = rate.get();
            r.setBook(book);
            r.setProfile(p);
            r.setContentBook(rateBook.getContentBook());
            r.setHelpful(rateBook.getHelpful());
            r.setUnderstand(rateBook.getUnderstand());
            r.setTotalRate(Math.round(avg * 100) / 100.0);
            rateBookService.save(r);
        }
        if (c.isPresent()) {
            Comment co = c.get();
            co.setRate(Math.round(avg * 100) / 100.0);
            co.setContent(rateBook.getContent());
            co.setCreateAt(LocalDateTime.now());
            return service.save(co);
        }
        Comment comment = new Comment();
        comment.setBook(book);
        comment.setProfile(p);
        comment.setCreateAt(LocalDateTime.now());
        comment.setType(RateType.RATE);
        comment.setContent(rateBook.getContent());
        comment.setRate(Math.round(avg * 100) / 100.0);
        return service.save(comment);
    }

    @GetMapping("/page/{pageId}")
    @Operation(summary = "lấy dánh sách comment theo page id")
    public List<Comment> getCommentsByPageId(@PathVariable("pageId") String id) {
        return service.findByPageId(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "lấy comment bằng comment id")
    public Comment getComment(@PathVariable("id") String id) {
        return service.findById(id).orElseThrow(() -> new NotFoundException("không tìm thấy comment có id là:" + id));
    }

    public Profile authenProfile(UserDto dto) {
        User u = userService.findByUserName(dto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        Optional<Profile> p = profileService.findByUserId(u.getId());
        if (p.isPresent()) {
            return p.get();
        } else throw new RuntimeException("không tìm thấy profile user có mssv: " + u.getMssv());
    }

    @GetMapping("/book/{bookId}")
    @Operation(summary = "lấy comment hoặc đánh giá theo book id")
    public List<Comment> getRatesByBookId(@PathVariable("bookId") String bookId, @RequestParam("type") String type) {
        Book b = bookService.findById(bookId).orElseThrow(() -> new NotFoundException("không tìm thấy book có id: " + bookId));
        if (type.equals("comment")) {
            return service.findByBookIdAndType(b.getId(), RateType.COMMENT);
        } else if (type.equals("rate"))
            return service.findByBookIdAndType(b.getId(), RateType.RATE);
        return null;
    }

    @PostMapping("/like/{id}")
    public Comment handleLike(@PathVariable("id") String id, @AuthenticationPrincipal UserDto dto) {
        Profile p = authenProfile(dto);
        Comment c = service.findById(id).orElseThrow(() -> new NotFoundException("không tìm thấy comment id: " + id));
        if (c.getLiker().contains(p)) {
            c.getLiker().remove(p);
            return service.save(c);
        } else {
            c.getLiker().add(p);
            return service.save(c);
        }
    }

    @PostMapping("/rate/{bookId}")
    @Operation(summary = "thêm comment vào sách")
    public Comment saveRate(@RequestBody String content, @AuthenticationPrincipal UserDto dto, @PathVariable("bookId") String bookId, @RequestParam("parent") Optional<String> parent) {
        if (content.startsWith("\"") && content.endsWith("\"")) {
            content = content.substring(1, content.length() - 1);
        }
        Profile p = authenProfile(dto);
        Book pb = bookService.findById(bookId).orElseThrow(() -> new NotFoundException("Không tìm thấy sách có id: " + bookId));
        Comment comment = new Comment();
        if (parent.isPresent() && !parent.get().equals("undefined")) {
            Comment f = service.findById(parent.get()).orElseThrow(() -> new NotFoundException("không tìm thấy comment id: " + parent.get()));
            f.setChildrenCount(f.getChildrenCount() + 1);
            Comment a = service.save(f);
            comment.setParent(a);
        }
        comment.setCreateAt(LocalDateTime.now());
        comment.setContent(content);
        comment.setProfile(p);
        comment.setBook(pb);
        comment.setType(RateType.RATE);
        return service.save(comment);
    }

    @PostMapping("/{pageId}")
    @Operation(summary = "thêm comment vào page")
    public Comment save(@RequestBody String content, @AuthenticationPrincipal UserDto dto, @PathVariable("pageId") String pageId, @RequestParam("parent") Optional<String> parent) {
        if (content.startsWith("\"") && content.endsWith("\"")) {
            content = content.substring(1, content.length() - 1);
        }
        Profile p = authenProfile(dto);
        PageBook pb = pageService.findById(pageId).orElseThrow(() -> new NotFoundException("Không tìm thấy page có id: " + pageId));
        Comment comment = new Comment();
        if (parent.isPresent() && !parent.get().equals("undefined")) {
            Comment f = service.findById(parent.get()).orElseThrow(() -> new NotFoundException("không tìm thấy comment id: " + parent.get()));
            comment.setParent(f);
            f.setChildrenCount(f.getChildrenCount() + 1);
            service.save(f);
        }
        comment.setCreateAt(LocalDateTime.now());
        comment.setContent(content);
        comment.setPageBook(pb);
        comment.setProfile(p);
        comment.setBook(pb.getBook());
        comment.setType(RateType.COMMENT);
        return service.save(comment);
    }

    @PostMapping("/book/{bookId}")
    @Operation(summary = "thêm comment vào book")
    public Comment saveByBookId(@RequestBody String content, @AuthenticationPrincipal UserDto dto, @PathVariable("bookId") String id, @RequestParam("parent") Optional<String> parent) {
        if (content.startsWith("\"") && content.endsWith("\"")) {
            content = content.substring(1, content.length() - 1);
        }

        Profile p = authenProfile(dto);
        Book pb = bookService.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy book có id: " + id));
        Comment comment = new Comment();
        if (parent.isPresent() && !parent.get().equals("undefined")) {
            Comment f = service.findById(parent.get()).orElseThrow(() -> new NotFoundException("không tìm thấy comment id: " + parent.get()));
            f.setChildrenCount(f.getChildrenCount() + 1);
            Comment a = service.save(f);
            comment.setParent(a);
        }
        comment.setCreateAt(LocalDateTime.now());
        comment.setContent(content);
        comment.setProfile(p);
        comment.setBook(pb);
        comment.setType(RateType.COMMENT);
        return service.save(comment);
    }

}

package fit.se.kltn.controller;

import fit.se.kltn.entities.*;
import fit.se.kltn.enums.EmoType;
import fit.se.kltn.enums.RateType;
import fit.se.kltn.services.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/computed")
public class ComputedBookController {
    @Qualifier("computedBookImpl")
    @Autowired
    private ComputedBookService service;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookInteractionService bookInteractionService;
    @Autowired
    private RateBookService rateBookService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatepageService ratepageService;
    @Autowired
    private PageInteractionService pageInteractionService;
    @PostMapping("/{bookId}")
    @Operation(summary = "thống kê tất cả các thuộc tính của book")
    public ComputedBook computed(@PathVariable("bookId") String bookId) {
        Book b = bookService.findById(bookId).orElseThrow(() -> new RuntimeException("không tìm thấy book có id: " + bookId));
        Optional<ComputedBook> com = service.findByBookId(bookId);
        List<BookInteraction> bi = bookInteractionService.findByBookId(b.getId());
        List<Comment> comments = commentService.findByBookIdAndType(bookId, RateType.COMMENT);
        List<PageInteraction> pageInteractions = pageInteractionService.findByBookId(bookId);
        List<RatePage> ratePages = ratepageService.findByBookId(bookId);

        Double readCount = !pageInteractions.isEmpty() ?pageInteractions.stream()
                .mapToDouble(PageInteraction::getRead)
                .sum():5;
        long save = !bi.isEmpty() ?bi.stream()
                .filter(BookInteraction::isFollowed)
                .count():0;
        long nominated = !bi.isEmpty() ?bi.stream()
                .filter(BookInteraction::isNominated)
                .count():0;
        List<RateBook> rateBooks = rateBookService.findByBookId(bookId);
        Double helpful = !rateBooks.isEmpty() ?rateBooks.stream()
                .mapToDouble(RateBook::getHelpful)
                .sum() / rateBooks.size():5;
        Double contentBook = !rateBooks.isEmpty() ?rateBooks.stream()
                .mapToDouble(RateBook::getContentBook)
                .sum() / rateBooks.size():5;
        Double understand = !rateBooks.isEmpty() ?rateBooks.stream()
                .mapToDouble(RateBook::getUnderstand)
                .sum() / rateBooks.size():5;
        Double totalRate = !rateBooks.isEmpty() ?rateBooks.stream()
                .mapToDouble(RateBook::getTotalRate)
                .sum() / rateBooks.size():5;
        Double contentPage = !ratePages.isEmpty() ?ratePages.stream()
                .mapToDouble(RatePage::getRate)
                .sum() / ratePages.size():5;
        int reviewCount = rateBooks.size();
        int commentCount = comments.size();
        long lover = !pageInteractions.isEmpty() ?pageInteractions.stream()
                .filter(pi -> EmoType.LOVE.equals(pi.getType()))
                .count():0;
        long sad = !pageInteractions.isEmpty() ?pageInteractions.stream()
                .filter(pi -> EmoType.SAD.equals(pi.getType()))
                .count():0;
        long angry = !pageInteractions.isEmpty() ?pageInteractions.stream()
                .filter(pi -> EmoType.ANGRY.equals(pi.getType()))
                .count():0;
        long fun = !pageInteractions.isEmpty() ?pageInteractions.stream()
                .filter(pi -> EmoType.FUN.equals(pi.getType()))
                .count():0;
        long like = !pageInteractions.isEmpty() ?pageInteractions.stream()
                .filter(pi -> EmoType.LIKE.equals(pi.getType()))
                .count():0;

        ComputedBook compu;
        if (com.isEmpty()) {
            compu = new ComputedBook();
            compu.setBook(b);
            compu.setContentBook(contentBook);
            compu.setHelpful(helpful);
            compu.setUnderstand(understand);
            compu.setTotalRate(totalRate);
            compu.setContentPage(contentPage);
            compu.setSave(save);
            compu.setFun(fun);
            compu.setAngry(angry);
            compu.setLike(like);
            compu.setLove(lover);
            compu.setSad(sad);
            compu.setCommentCount(commentCount);
            compu.setNominatedCount(nominated);
            compu.setReviewCount(reviewCount);
            compu.setReadCount(readCount);
            return service.save(compu);
        }
        compu = com.get();
        compu.setContentBook(contentBook);
        compu.setHelpful(helpful);
        compu.setUnderstand(understand);
        compu.setTotalRate(totalRate);
        compu.setContentPage(contentPage);
        compu.setSave(save);
        compu.setFun(fun);
        compu.setAngry(angry);
        compu.setLike(like);
        compu.setLove(lover);
        compu.setSad(sad);
        compu.setCommentCount(commentCount);
        compu.setNominatedCount(nominated);
        compu.setReviewCount(reviewCount);
        compu.setReadCount(readCount);
        return service.save(compu);
    }
}

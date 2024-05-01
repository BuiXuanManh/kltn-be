package fit.se.kltn.controller;

import fit.se.kltn.entities.Comment;
import fit.se.kltn.entities.ComputedPage;
import fit.se.kltn.entities.PageBook;
import fit.se.kltn.entities.PageInteraction;
import fit.se.kltn.enums.EmoType;
import fit.se.kltn.enums.RateType;
import fit.se.kltn.exception.NotFoundException;
import fit.se.kltn.services.CommentService;
import fit.se.kltn.services.ComputedPageService;
import fit.se.kltn.services.PageInteractionService;
import fit.se.kltn.services.PageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/computed/pages")
public class ComputedPageController {
    @Qualifier("computedPageImpl")
    @Autowired
    private ComputedPageService service;
    @Autowired
    private PageInteractionService pageInteractionService;
    @Autowired
    private PageService pageService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/{pageId}")
    @Operation(summary = "lấy thống kê trang theo pageId")
    public ComputedPage getComputedPage(@PathVariable("pageId") String id) {
        Optional<ComputedPage> find = service.findByPageId(id);
        return find.orElse(null);
    }

    @PostMapping("/{pageId}")
    @Operation(summary = "thống kê các tương tác thuộc tính của page")
    public ComputedPage addComputedPage(@PathVariable("pageId") String id) {
        PageBook p = pageService.findById(id).orElseThrow(() -> new NotFoundException("không tìm thấy page có id: " + id));
        Optional<ComputedPage> find = service.findByPageId(id);
        List<PageInteraction> pageInteractions = pageInteractionService.findByPageBookId(id);
        List<Comment> comments = commentService.findByPageId(id);
        int commentCount = comments.size();
        Double readCount = !pageInteractions.isEmpty() ? pageInteractions.stream()
                .mapToDouble(PageInteraction::getRead)
                .sum() : 0;
        long lover = !pageInteractions.isEmpty() ? pageInteractions.stream()
                .filter(pi -> EmoType.LOVE.equals(pi.getType()))
                .count() : 0;
        long sad = !pageInteractions.isEmpty() ? pageInteractions.stream()
                .filter(pi -> EmoType.SAD.equals(pi.getType()))
                .count() : 0;
        long angry = !pageInteractions.isEmpty() ? pageInteractions.stream()
                .filter(pi -> EmoType.ANGRY.equals(pi.getType()))
                .count() : 0;
        long fun = !pageInteractions.isEmpty() ? pageInteractions.stream()
                .filter(pi -> EmoType.FUN.equals(pi.getType()))
                .count() : 0;
        long like = !pageInteractions.isEmpty() ? pageInteractions.stream()
                .filter(pi -> EmoType.LIKE.equals(pi.getType()))
                .count() : 0;
        long mark = !pageInteractions.isEmpty() ?pageInteractions.stream()
                .filter(PageInteraction::isMark)
                .count():0;
        if (find.isPresent()) {
            ComputedPage computed = find.get();
            computed.setEmotion(lover + like + sad + angry + fun);
            computed.setReadCount(readCount);
            computed.setCommentCount(commentCount);
            computed.setMark(mark);
            return service.save(computed);
        }
        ComputedPage computed = new ComputedPage();
        computed.setPageBook(p);
        computed.setEmotion(lover + like + sad + angry + fun);
        computed.setReadCount(readCount);
        computed.setCommentCount(commentCount);
        computed.setMark(mark);
        return service.save(computed);
    }
}

package fit.se.kltn.controller;

import fit.se.kltn.entities.*;
import fit.se.kltn.enums.RateType;
import fit.se.kltn.exception.NotFoundException;
import fit.se.kltn.services.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
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
    @Qualifier("pageInteractionImpl")
    @Autowired
    private PageInteractionService interactionService;
    @GetMapping
    @Operation(summary = "lấy danh sách tất cả comment")
    public List<Comment> getAll() {
        return service.findAll();
    }
    @GetMapping("/page/{pageId}")
    @Operation(summary = "lấy dánh sách comment theo page id")
    public List<Comment> getCommentsByPageId(@PathVariable("pageId") String id){
        return service.findByPageId(id);
    }
    @GetMapping("/{id}")
    @Operation(summary = "lấy comment bằng comment id")
    public Comment getComment(@PathVariable("id") String id) {
        return service.findById(id).orElseThrow(() -> new NotFoundException("không tìm thấy comment có id là:" + id));
    }

    @PostMapping("/{pageId}/{profileId}")
    @Operation(summary = "thêm comment với param rate=comment, thêm rate với param rate=rate")
    public Comment save(@RequestBody Comment comment, @PathVariable("profileId") String profileId, @PathVariable("pageId") String pageId, @RequestParam("rate") String rate) {
        Profile p = profileService.findById(profileId).orElseThrow(() -> new NotFoundException("không tìm thấy profile có id là: " + profileId));
        PageBook pb = pageService.findById(pageId).orElseThrow(() -> new NotFoundException("Không tìm thấy page có id: " + pageId));
        Optional<PageInteraction> neww = interactionService.findByProfileIDAndPageBookId(profileId, pageId);
        if (neww.isPresent()) {
            PageInteraction bi = neww.get();
            List<Comment> list = bi.getComments();
            comment.setCreateAt(LocalDateTime.now());
            if (rate.equals("comment"))
                comment.setType(RateType.COMMENT);
            if (rate.equals("rate")) {
                comment.setType(RateType.RATE);
            }
            Comment com = service.save(comment);
            list.add(com);
            bi.setComments(list);
            interactionService.save(bi);
            return com;
        }
        PageInteraction bi = new PageInteraction();
        bi.setPageBook(pb);
        bi.setProfile(p);
        comment.setCreateAt(LocalDateTime.now());
        if (rate.equals("comment"))
            comment.setType(RateType.COMMENT);
        if (rate.equals("rate")) {
            comment.setType(RateType.RATE);
        }
        Comment com = service.save(comment);
        bi.setComments(List.of(com));
        interactionService.save(bi);
        return com;
    }


}

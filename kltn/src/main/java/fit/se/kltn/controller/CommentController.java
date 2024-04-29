package fit.se.kltn.controller;

import fit.se.kltn.dto.UserDto;
import fit.se.kltn.entities.*;
import fit.se.kltn.enums.RateType;
import fit.se.kltn.exception.NotFoundException;
import fit.se.kltn.services.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @Autowired
    private UserService userService;

    @GetMapping("/page/get/{pageId}")
    @Operation(summary = "lấy comment hoặc rate theo pageId và profile id")
    public Comment getComment(@AuthenticationPrincipal UserDto dto, @PathVariable("pageId") String pageId, @RequestParam("type") String type) {
        Profile p = authenProfile(dto);
        PageBook page = pageService.findById(pageId).orElseThrow(() -> new NotFoundException("khÔng tìm thấy page có id: " + pageId));
        if (type.equals("rate"))
            return service.findByProfileIdAndPageIdAndType(p.getId(), page.getId(), RateType.RATE).orElseThrow(() -> new RuntimeException("không tìm thấy " + type));
        return service.findByProfileIdAndPageIdAndType(p.getId(), page.getId(), RateType.COMMENT).orElseThrow(() -> new RuntimeException("không tìm thấy " + type));

    }

    @GetMapping
    @Operation(summary = "lấy danh sách tất cả comment")
    public List<Comment> getAll() {
        return service.findAll();
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

    @PostMapping("/{pageId}")
    @Operation(summary = "thêm comment với param rate=comment, thêm rate với param rate=rate")
    public Comment save(@RequestBody String content, @AuthenticationPrincipal UserDto dto, @PathVariable("pageId") String pageId, @RequestParam("rate") String rate) {
        if (content.startsWith("\"") && content.endsWith("\"")) {
            content = content.substring(1, content.length() - 1);
        }

        Profile p = authenProfile(dto);
        PageBook pb = pageService.findById(pageId).orElseThrow(() -> new NotFoundException("Không tìm thấy page có id: " + pageId));
        if (rate.equals("rate")) {
            Optional<Comment> c = service.findByProfileIdAndPageIdAndType(p.getId(),pageId,RateType.RATE);
            if(c.isPresent()) {
                Comment co = c.get();
                co.setRate(Double.parseDouble(content));
                return service.save(co);
            }
            Comment comment = new Comment();
            comment.setPageBook(pb);
            comment.setProfile(p);
            comment.setCreateAt(LocalDateTime.now());
            comment.setType(RateType.RATE);
            comment.setRate(Double.parseDouble(content));
            return service.save(comment);
        }
        Comment comment = new Comment();
        comment.setCreateAt(LocalDateTime.now());
        comment.setContent(content);
        comment.setPageBook(pb);
        comment.setProfile(p);
        comment.setType(RateType.COMMENT);
        return service.save(comment);
    }


}

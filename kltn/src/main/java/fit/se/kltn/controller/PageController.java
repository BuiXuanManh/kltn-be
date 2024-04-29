package fit.se.kltn.controller;

import fit.se.kltn.dto.UserDto;
import fit.se.kltn.entities.*;
import fit.se.kltn.enums.EmoType;
import fit.se.kltn.exception.NotFoundException;
import fit.se.kltn.services.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pages")
public class PageController {
    @Qualifier("pageImpl")
    @Autowired
    private PageService service;
    @Autowired
    private BookService bookService;
    @Qualifier("pageInteractionImpl")
    @Autowired
    private PageInteractionService interactionService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;
    @Autowired
    private RatepageService ratepageService;

    @GetMapping("/book/{bookId}")
    @Operation(summary = "lấy danh sách trang theo book id")
    public List<PageBook> getPagesByBookId(@PathVariable("bookId") String id){
        return service.findByBookId(id);
    }
    @PostMapping("/ratePage/{id}")
    @Operation(summary = "thêm đánh giá vào trang")
    public RatePage addRatePage(@PathVariable("id") String id, @AuthenticationPrincipal UserDto dto, @RequestBody Double point) {
        Profile p = authenProfile(dto);
        PageBook page = service.findById(id).orElseThrow(() -> new NotFoundException("khÔng tìm thấy page có id: " + id));
        Optional<RatePage> rate = ratepageService.findByProfileIdAndPageId(p.getId(), page.getId());
        if (rate.isEmpty()) {
            RatePage r = new RatePage();
            r.setPage(page);
            r.setProfile(p);
            r.setRate(point);
            return ratepageService.save(r);
        }
        RatePage r = rate.get();
        r.setRate(point);
        return ratepageService.save(r);
    }

    @GetMapping("/ratePage/{id}")
    public RatePage findRatePageByProfileIdAndPageId(@PathVariable("id") String id, @AuthenticationPrincipal UserDto dto) {
        Profile p = authenProfile(dto);
        PageBook page = service.findById(id).orElseThrow(() -> new NotFoundException("khÔng tìm thấy page có id: " + id));
        RatePage rate = ratepageService.findByProfileIdAndPageId(p.getId(), page.getId()).orElseThrow(() -> new NotFoundException("không tìm thấy đánh giá trang"));
        return rate;
    }

    public Profile authenProfile(UserDto dto) {
        User u = userService.findByUserName(dto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        Optional<Profile> p = profileService.findByUserId(u.getId());
        if (p.isPresent()) {
            return p.get();
        } else throw new RuntimeException("không tìm thấy profile user có mssv: "+ u.getMssv());
    }

    @GetMapping("/interactions/profile/{pageId}")
    @Operation(summary = "lấy danh sách tương tác theo pageId")
    public List<PageInteraction> getInteractionByPageId(@PathVariable("pageId") String pageId) {
        return interactionService.findByPageBookId(pageId);
    }

    @GetMapping("/interactions/{id}")
    @Operation(summary = "lấy danh sách tương tác theo profile Id và pageId")
    public PageInteraction getInteractionByPageIdandProfileId(@AuthenticationPrincipal UserDto dto, @PathVariable("id") String id) {
        Profile p = authenProfile(dto);
        PageInteraction interaction = validInteraction(id, p.getId());
        return interaction;
    }

    @GetMapping("/interactions")
    @Operation(summary = "lấy danh sách tương tác theo profile Id")
    public List<PageInteraction> getInteractionByPageId(@AuthenticationPrincipal UserDto dto) {
        Profile p = authenProfile(dto);
        return interactionService.finByProfileId(p.getId());
    }

    @PostMapping("/flag/{id}")
    @Operation(summary = "đánh dấu trang, hủy đánh dấy")
    public PageInteraction mark(@PathVariable("id") String id, @AuthenticationPrincipal UserDto dto, @RequestParam("type") Boolean type) {
        Profile p = authenProfile(dto);
        PageInteraction interaction = validInteraction(id, p.getId());
        if (type)
            interaction.setMark(true);
        else interaction.setMark(false);
        interactionService.save(interaction);
        return interaction;
    }

    public PageInteraction validInteraction(String id, String profileId) {
        PageBook page = service.findById(id).orElseThrow(() -> new NotFoundException("không tìm thấy page có id: " + id));
        Profile p = profileService.findById(profileId).orElseThrow(() -> new NotFoundException("không tìm thấy profile có id là: " + profileId));
        PageInteraction interaction = interactionService.findByProfileIDAndPageBookId(p.getId(), page.getId()).orElseThrow(() -> new NotFoundException("không tìm thấy tương tác"));
        return interaction;
    }

    @PostMapping("/emotion/{id}")
    @Operation(summary = "thêm biểu cảm cho trang")
    public PageInteraction addEmotion(@RequestParam("type") EmoType type, @PathVariable("id") String id, @AuthenticationPrincipal UserDto dto) {
        Profile p = authenProfile(dto);
        PageInteraction interaction = validInteraction(id, p.getId());
        interaction.setType(type);
        interactionService.save(interaction);
        return interaction;
    }

    @GetMapping("/{bookId}/{pageNo}")
    @Operation(summary = "Tìm trang theo book Id và số trang")
    public PageBook findByPageNo(@PathVariable("pageNo") int pageNo, @PathVariable("bookId") String id) {
        Optional<Book> b = bookService.findById(id);
        if (b.isEmpty()) {
            throw new RuntimeException("không tìm thấy book có id: " + id);
        }
        List<PageBook> list = service.findByBookId(id);
        if (pageNo <= 0 || pageNo > list.size()) {
            throw new RuntimeException("Không tìm thấy số trang: " + pageNo);
        }
        for (PageBook pageBook : list) {
            if (pageBook.getPageNo() == pageNo) {
                return pageBook;
            }
        }
        throw new RuntimeException("Error");
    }
}

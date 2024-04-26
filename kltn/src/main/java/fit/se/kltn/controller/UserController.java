package fit.se.kltn.controller;

import fit.se.kltn.dto.UserDto;
import fit.se.kltn.entities.Book;
import fit.se.kltn.entities.BookInteraction;
import fit.se.kltn.entities.Profile;
import fit.se.kltn.entities.User;
import fit.se.kltn.exception.NotFoundException;
import fit.se.kltn.services.BookInteractionService;
import fit.se.kltn.services.BookService;
import fit.se.kltn.services.ProfileService;
import fit.se.kltn.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService service;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private BookService bookService;
    @Qualifier("bookInteractionImpl")
    @Autowired
    private BookInteractionService interactionService;

    @PostMapping("/interaction/read/{bookId}/{page}")
    @Operation(summary = "Cập nhập sách đang đọc của user")
    public Profile updateBookInteraction(@AuthenticationPrincipal UserDto dto, @PathVariable("bookId") String bookId, @PathVariable("page") int page) {
        authenProfile(dto);
        Book b = bookService.findById(bookId).orElseThrow(() -> new NotFoundException("không tìm thấy book có id: " + bookId));
        Profile p = profileService.findByUserId(dto.getId()).orElseThrow(() -> new NotFoundException("không tìm thấy profile có id: " + dto.getId()));
        BookInteraction find = interactionService.getBookInteraction(b.getId(), p.getId()).orElseThrow(() -> new NotFoundException("không tìm thấy tương tác "));

        List<BookInteraction> lb=b.getInteractions();
        lb.removeIf(i->i.getId().equals(find.getId()));
        find.setReadCount(page);
        lb.add(find);
        b.setInteractions(lb);
        bookService.save(b);
        List<BookInteraction> lp = p.getInteractions();
        lp.removeIf(i->i.getId().equals(find.getId()));
        find.setReadCount(page);
        lp.add(find);
        interactionService.save(find);
        p.setInteractions(lp);
        return profileService.save(p);
    }

    public Profile authenProfile(UserDto dto) {
        User u = service.findByUserName(dto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        Optional<Profile> p = profileService.findByUserId(u.getId());
        if (p.isPresent()) {
            return p.get();
        } else throw new RuntimeException("profile not found");
    }

    @GetMapping("/profile")
    @Operation(summary = "Lấy thông tin cá nhân profile")
    public Profile getProfile(@AuthenticationPrincipal UserDto dto) {
        return authenProfile(dto);
    }

    @PostMapping
    @Operation(summary = "Thêm user")
    public User save(User user) {
        return service.save(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Tìm user")
    public User findUser(@PathVariable("id") String id) {
        Optional<User> u = service.findById(id);
        if (u.isEmpty()) throw new RuntimeException("không tìm thấy user có id " + id);
        return u.get();
    }

    @GetMapping("/getAll")
    @Operation(summary = "lấy tất cả danh sách profile")
    public List<Profile> getAll() {
        return profileService.findAll();
    }
}

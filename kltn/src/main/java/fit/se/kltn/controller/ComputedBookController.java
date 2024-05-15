package fit.se.kltn.controller;

import fit.se.kltn.dto.BookGenresDto;
import fit.se.kltn.dto.BookPageDto;
import fit.se.kltn.dto.ComputedDto;
import fit.se.kltn.entities.*;
import fit.se.kltn.enums.BookStatus;
import fit.se.kltn.enums.EmoType;
import fit.se.kltn.enums.RateType;
import fit.se.kltn.enums.UserStatus;
import fit.se.kltn.services.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/computed/books")
@Slf4j
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
    @Autowired
    private UserService userService;
    @GetMapping("/user/date")
    @Operation(summary = "get list user date")
    public List<Long> getUserByDate() {
        return pageInteractionService.findUserByDate();
    }
    @GetMapping("/genres")
    @Operation(summary = "get list genres percent")
    public Map<String, Integer> getGenresByPercent() {
        List<Book> list = bookService.findAll();
        Map<String, Long> genreCountMap = new HashMap<>();
        long totalGenres = 0;

        // Đếm số lượng sách của mỗi thể loại
        for (Book b : list) {
            List<Genre> genres = b.getGenres();
            for (Genre genre : genres) {
                String genreName = genre.getName();
                genreCountMap.put(genreName, genreCountMap.getOrDefault(genreName, 0L) + 1);
                totalGenres++;
            }
        }

        // Tính tỷ lệ phần trăm và làm tròn
        Map<String, Integer> genrePercentMap = new HashMap<>();
        int totalPercent = 0;
        for (Map.Entry<String, Long> entry : genreCountMap.entrySet()) {
            String genreName = entry.getKey();
            Long count = entry.getValue();
            int percent = (int) Math.round((double) count / totalGenres * 100);
            genrePercentMap.put(genreName, percent);
            totalPercent += percent;
        }

        // Điều chỉnh để tổng cộng là 100%
        int difference = 100 - totalPercent;
        if (difference != 0) {
            for (Map.Entry<String, Integer> entry : genrePercentMap.entrySet()) {
                String genreName = entry.getKey();
                int percent = entry.getValue();
                genrePercentMap.put(genreName, percent + difference);
                break;
            }
        }

        // Sắp xếp theo phần trăm giảm dần
        List<Map.Entry<String, Integer>> sortedGenres = new ArrayList<>(genrePercentMap.entrySet());
        sortedGenres.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Lấy ra 3 thể loại có phần trăm cao nhất và tổng hợp phần còn lại
        Map<String, Integer> result = new LinkedHashMap<>();
        int othersPercent = 0;
        for (int i = 0; i < sortedGenres.size(); i++) {
            if (i < 3) {
                result.put(sortedGenres.get(i).getKey(), sortedGenres.get(i).getValue());
            } else {
                othersPercent += sortedGenres.get(i).getValue();
            }
        }

        // Thêm mục "Other" nếu có các thể loại còn lại
        if (othersPercent > 0) {
            result.put("Thể loại khác", othersPercent);
        }

        return result;
    }

    @GetMapping("/nominate/date")
    @Operation(summary = "get list nominate date")
    public List<Long> getNominateByDate() {
       return bookInteractionService.findRecentNominationsByDate();
    }
    @GetMapping("/emo/date")
    @Operation(summary = "get list emo date")
    public List<Long> getEmoByDate() {
        return pageInteractionService.findRecentEmoByDate();
    }
    @GetMapping("/comment/date")
    @Operation(summary = "get list emo date")
    public List<Long> getCommentByDate() {
        return pageInteractionService.findRecentCommentByDate();
    }
    @GetMapping("/rate/date")
    @Operation(summary = "get list emo date")
    public List<Long> getRateByDate() {
        return pageInteractionService.findRecentRateByDate();
    }
    @GetMapping("/read/date")
    @Operation(summary = "get list red date")
    public List<Long> getReadByDate() {
        return pageInteractionService.findRecentReadsByDate();
    }
    @GetMapping("/total")
    public ComputedDto getTotal() {
        List<PageInteraction> pageInteractions= pageInteractionService.getInteractions();
        int totalReads = !pageInteractions.isEmpty() ? pageInteractions.stream()
                .mapToInt(PageInteraction::getRead)
                .sum() : 0;
        int totalReviews = pageInteractionService.findComputedByRateCount().size();
        int totalComments = pageInteractionService.findComputedByComment().size();
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
        long totalEmotions = lover + sad + angry + fun + like;
        int activeUser = userService.findByStatus(UserStatus.ACTIVE).size();
        int bookCount = bookService.findByStatus(BookStatus.ACTIVE).size();
        ComputedDto compu = new ComputedDto();
        compu.setReadCount(totalReads);
        compu.setReviewCount(totalReviews);
        compu.setTotalEmotion(totalEmotions);
        compu.setCommentCount(totalComments);
        compu.setActiveUser(activeUser);
        compu.setBookCount(bookCount);
        return compu;
    }
    @GetMapping("/{bookId}")
    @Operation(summary = "lấy thống kê sách theo sách id")
    public ComputedBook getComputedBook(@PathVariable("bookId") String bookId) {
        Optional<ComputedBook> find = service.findByBookId(bookId);
        return find.orElse(null);
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
    @PostMapping("/genres/find")
    public BookPageDto findBookByGenres(@RequestBody BookGenresDto dto,
                                        @RequestParam(value = "page", required = false) Optional<Integer> page,
                                        @RequestParam(value = "size", required = false) Optional<Integer> size) {
        if (dto == null) throw new RuntimeException("dto is null");
        List<Book> list = switch (dto.getField()) {
            case "love" -> pageInteractionService.findComputedByLove();
            case "save" -> pageInteractionService.findComputedBySave();
            case "comment" -> pageInteractionService.findComputedByComment();
            case "rate" -> pageInteractionService.findComputedByRate();
            case "rateCount" -> pageInteractionService.findComputedByRateCount();
            case "readtotal" -> pageInteractionService.findRecentReads("total");
            case "readday" -> pageInteractionService.findRecentReads("day");
            case "readweek" -> pageInteractionService.findRecentReads("week");
            case "readmonth" -> pageInteractionService.findRecentReads("month");
            case "nominatetotal" -> bookInteractionService.findRecentNominations("total");
            case "nominateday" -> bookInteractionService.findRecentNominations("day");
            case "nominateweek" -> bookInteractionService.findRecentNominations("week");
            case "nominatemonth" -> bookInteractionService.findRecentNominations("month");
            case "createdAt", "new" -> bookService.findByCreatedAt();
            case "updateDate" -> bookService.findByUpdateAt();
            default -> throw new IllegalStateException("Unexpected value: " + dto.getField());
        };
        int currentPage = page.orElse(1);
        int currentSize = size.orElse(10);
        if (dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
            list = list.stream().filter(book -> book.getTitle().toLowerCase().contains(dto.getKeyword().toLowerCase())).collect(Collectors.toList());
        }
        if (dto.getGenres().isEmpty())
            return converPage(list, currentPage, currentSize, dto.getField());
        return getFilteredBooks(dto.getGenres(), list, currentPage, currentSize, dto.getField());
    }

    public BookPageDto getFilteredBooks(List<Genre> genres, List<Book> books, int pageNo, int pageSize, String field) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenres().stream().anyMatch(genre -> genres.stream().anyMatch(g -> g.getId().equals(genre.getId())))) {
                result.add(book);
            }
        }
        return converPage(result, pageNo, pageSize, field);
    }

    @GetMapping("/nominate")
    @Operation(summary = "Lấy danh sách sách đề cử theo ngày") // Updated summary
    public BookPageDto getNominatedBooksByDate(@RequestParam("date") String date,
                                               @RequestParam(value = "page", required = false) Optional<Integer> page,
                                               @RequestParam(value = "size", required = false) Optional<Integer> size) {
        List<Book> nominations = bookInteractionService.findRecentNominations(date);
        int currentPage = page.orElse(1);
        int currentSize = size.orElse(10);
        return converPage(nominations, currentPage, currentSize, "nominate" + date);
    }

    @GetMapping("/read")
    public BookPageDto getReadBookByDate(@RequestParam("date") String date,
                                         @RequestParam(value = "page", required = false) Optional<Integer> page,
                                         @RequestParam(value = "size", required = false) Optional<Integer> size) {
        List<Book> pageInteractions = pageInteractionService.findRecentReads(date);
        int currentPage = page.orElse(1);
        int currentSize = size.orElse(10);
        return converPage(pageInteractions, currentPage, currentSize, "read" + date);
    }

    @GetMapping("/find")
    public BookPageDto getComputedBookByType(@RequestParam("type") String type,
                                             @RequestParam(value = "page", required = false) Optional<Integer> page,
                                             @RequestParam(value = "size", required = false) Optional<Integer> size) {
        List<Book> pageInteractions = switch (type) {
            case "love" -> pageInteractionService.findComputedByLove();
            case "save" -> pageInteractionService.findComputedBySave();
            case "comment" -> pageInteractionService.findComputedByComment();
            case "rate" -> pageInteractionService.findComputedByRate();
            case "rateCount" -> pageInteractionService.findComputedByRateCount();
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        };
        int currentPage = page.orElse(1);
        int currentSize = size.orElse(10);
        return converPage(pageInteractions, currentPage, currentSize, type);
    }

    @PostMapping("/interaction/{bookId}")
    @Operation(summary = "thống kê tất cả các tương tác của book")
    public ComputedBook computedInteraction(@PathVariable("bookId") String bookId) {
        Book b = bookService.findById(bookId).orElseThrow(() -> new RuntimeException("không tìm thấy book có id: " + bookId));
        Optional<ComputedBook> com = service.findByBookId(bookId);
        List<BookInteraction> bi = bookInteractionService.findByBookId(b.getId());
        long save = !bi.isEmpty() ? bi.stream()
                .filter(BookInteraction::isFollowed)
                .count() : 0;
        long nominated = !bi.isEmpty() ? bi.stream()
                .filter(BookInteraction::isNominated)
                .count() : 0;
        if (com.isEmpty()) {
            ComputedBook compu = new ComputedBook();
            compu.setBook(b);
            compu.setSave(save);
            compu.setNominatedCount(nominated);
            return service.save(compu);
        }
        ComputedBook compu = com.get();
        compu.setSave(save);
        compu.setNominatedCount(nominated);
        return service.save(compu);
    }

    @PostMapping("/page/{bookId}")
    @Operation(summary = "thống kê tất cả các tương tác page của book")
    public ComputedBook computedPage(@PathVariable("bookId") String bookId) {
        Book b = bookService.findById(bookId).orElseThrow(() -> new RuntimeException("không tìm thấy book có id: " + bookId));
        Optional<ComputedBook> com = service.findByBookId(bookId);
        List<PageInteraction> pageInteractions = pageInteractionService.findByBookId(bookId);
        List<RatePage> ratePages = ratepageService.findByBookId(bookId);
        Double contentPage = !ratePages.isEmpty() ? ratePages.stream()
                .mapToDouble(RatePage::getRate)
                .sum() / ratePages.size() : 5;
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
        long mark = !pageInteractions.isEmpty() ? pageInteractions.stream()
                .filter(PageInteraction::isMark)
                .count() : 0;
        if (com.isEmpty()) {
            ComputedBook compu = new ComputedBook();
            compu.setBook(b);
            compu.setMark(mark);
            compu.setContentPage(contentPage);
            compu.setFun(fun);
            compu.setAngry(angry);
            compu.setLike(like);
            compu.setLove(lover);
            compu.setSad(sad);
            compu.setReadCount(readCount);
            return service.save(compu);
        }
        ComputedBook compu = com.get();
        compu.setMark(mark);
        compu.setContentPage(contentPage);
        compu.setFun(fun);
        compu.setAngry(angry);
        compu.setLike(like);
        compu.setLove(lover);
        compu.setSad(sad);
        compu.setReadCount(readCount);
        return service.save(compu);
    }

    @PostMapping("/rate/{bookId}")
    @Operation(summary = "thống kê tất cả các tương tác rate của book")
    public ComputedBook computedRate(@PathVariable("bookId") String bookId) {
        Book b = bookService.findById(bookId).orElseThrow(() -> new RuntimeException("không tìm thấy book có id: " + bookId));
        Optional<ComputedBook> com = service.findByBookId(bookId);
        List<RateBook> rateBooks = rateBookService.findByBookId(bookId);
        Double helpful = !rateBooks.isEmpty() ? rateBooks.stream()
                .mapToDouble(RateBook::getHelpful)
                .sum() / rateBooks.size() : 5;
        Double contentBook = !rateBooks.isEmpty() ? rateBooks.stream()
                .mapToDouble(RateBook::getContentBook)
                .sum() / rateBooks.size() : 5;
        Double understand = !rateBooks.isEmpty() ? rateBooks.stream()
                .mapToDouble(RateBook::getUnderstand)
                .sum() / rateBooks.size() : 5;
        Double totalRate = !rateBooks.isEmpty() ? rateBooks.stream()
                .mapToDouble(RateBook::getTotalRate)
                .sum() / rateBooks.size() : 5;
        int reviewCount = rateBooks.size();
        if (com.isEmpty()) {
            ComputedBook compu = new ComputedBook();
            compu.setBook(b);
            compu.setContentBook(contentBook);
            compu.setHelpful(helpful);
            compu.setUnderstand(understand);
            compu.setTotalRate(totalRate);
            compu.setReviewCount(reviewCount);
            return service.save(compu);
        }
        ComputedBook compu = com.get();
        compu.setContentBook(contentBook);
        compu.setHelpful(helpful);
        compu.setUnderstand(understand);
        compu.setTotalRate(totalRate);
        compu.setReviewCount(reviewCount);
        return service.save(compu);
    }

    @PostMapping("/comment/{bookId}")
    @Operation(summary = "thống kê tất cả các tương tác comment của book")
    public ComputedBook computedComment(@PathVariable("bookId") String bookId) {
        Book b = bookService.findById(bookId).orElseThrow(() -> new RuntimeException("không tìm thấy book có id: " + bookId));
        Optional<ComputedBook> com = service.findByBookId(bookId);
        List<Comment> comments = commentService.findByBookIdAndType(bookId, RateType.COMMENT);
        int commentCount = comments.size();
        if (com.isEmpty()) {
            ComputedBook compu = new ComputedBook();
            compu.setBook(b);
            compu.setCommentCount(commentCount);
            return service.save(compu);
        }
        ComputedBook compu = com.get();
        compu.setCommentCount(commentCount);
        return service.save(compu);
    }

    @PostMapping("/{bookId}")
    @Operation(summary = "thống kê tất cả các tương tác của book")
    public ComputedBook computed(@PathVariable("bookId") String bookId) {
        Book b = bookService.findById(bookId).orElseThrow(() -> new RuntimeException("không tìm thấy book có id: " + bookId));
        Optional<ComputedBook> com = service.findByBookId(bookId);
        List<BookInteraction> bi = bookInteractionService.findByBookId(b.getId());
        List<Comment> comments = commentService.findByBookIdAndType(bookId, RateType.COMMENT);
        List<PageInteraction> pageInteractions = pageInteractionService.findByBookId(bookId);
        List<RatePage> ratePages = ratepageService.findByBookId(bookId);
        Double readCount = !pageInteractions.isEmpty() ? pageInteractions.stream()
                .mapToDouble(PageInteraction::getRead)
                .sum() : 0;
        long save = !bi.isEmpty() ? bi.stream()
                .filter(BookInteraction::isFollowed)
                .count() : 0;
        long nominated = !bi.isEmpty() ? bi.stream()
                .filter(BookInteraction::isNominated)
                .count() : 0;
        List<RateBook> rateBooks = rateBookService.findByBookId(bookId);
        Double helpful = !rateBooks.isEmpty() ? rateBooks.stream()
                .mapToDouble(RateBook::getHelpful)
                .sum() / rateBooks.size() : 5;
        Double contentBook = !rateBooks.isEmpty() ? rateBooks.stream()
                .mapToDouble(RateBook::getContentBook)
                .sum() / rateBooks.size() : 5;
        Double understand = !rateBooks.isEmpty() ? rateBooks.stream()
                .mapToDouble(RateBook::getUnderstand)
                .sum() / rateBooks.size() : 5;
        Double totalRate = !rateBooks.isEmpty() ? rateBooks.stream()
                .mapToDouble(RateBook::getTotalRate)
                .sum() / rateBooks.size() : 5;
        Double contentPage = !ratePages.isEmpty() ? ratePages.stream()
                .mapToDouble(RatePage::getRate)
                .sum() / ratePages.size() : 5;
        int reviewCount = rateBooks.size();
        int commentCount = comments.size();
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
        if (com.isEmpty()) {
            ComputedBook compu = new ComputedBook();
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
        ComputedBook compu = com.get();
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

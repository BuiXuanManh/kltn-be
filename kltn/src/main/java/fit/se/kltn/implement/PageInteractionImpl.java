package fit.se.kltn.implement;

import fit.se.kltn.dto.BookComputed;
import fit.se.kltn.entities.Book;
import fit.se.kltn.entities.PageInteraction;
import fit.se.kltn.repositoties.BookRepository;
import fit.se.kltn.repositoties.PageInteractionRepository;
import fit.se.kltn.services.PageInteractionService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class PageInteractionImpl implements PageInteractionService {
    @Autowired
    private PageInteractionRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<PageInteraction> getInteractions() {
        return repository.findAll();
    }

    @Override
    public Optional<PageInteraction> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<PageInteraction> finByProfileId(String id) {
        return repository.findByProfile_Id(id);
    }

    @Override
    public List<PageInteraction> findByPageBookId(String id) {
        return repository.findByPageBook_Id(id);
    }

    @Override
    public Optional<PageInteraction> findByProfileIDAndPageBookId(String profileId, String pageBookId) {
        return repository.findByProfile_IdAndPageBook_Id(profileId, pageBookId);
    }

    @Override
    public PageInteraction save(PageInteraction interaction) {
        return repository.save(interaction);
    }

    @Override
    public List<PageInteraction> findByBookId(String bookId) {
        return repository.findByPageBook_Book_Id(bookId);
    }

    @Override
    public List<Book> findRecentReads(String date) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDate;
        switch (date) {
            case "day":
                startDate = today.minusDays(1);
                break;
            case "week":
                startDate = today.minusDays(7);
                break;
            case "month":
                startDate = today.minusMonths(1);
                break;
            case "total":
                return findRecentReadsByPage();
            default:
                throw new IllegalArgumentException("Invalid date: " + date);
        }
        return findRecentReadsByPage(startDate, today);
    }

    @Override
    public List<Long> findRecentReadsByDate() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime day1 = today.minusDays(1);
        LocalDateTime day2 = today.minusDays(2);
        LocalDateTime day3 = today.minusDays(3);
        LocalDateTime day4 = today.minusDays(4);
        LocalDateTime day5 = today.minusDays(5);
        List<Long> list = new ArrayList<>();
        long l = findRecentReadByDate(today);
        list.add(0, l);
        long l0 = findRecentReadByDate(day1);
        list.add(0, l0);
        long l1 = findRecentReadByDate(day2);
        list.add(0, l1);
        long l2 = findRecentReadByDate(day3);
        list.add(0, l2);
        long l3 = findRecentReadByDate(day4);
        list.add(0, l3);
        long l4 = findRecentReadByDate(day5);
        list.add(0, l4);
        return list;
    }

    @Override
    public List<Long> findRecentEmoByDate() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime day1 = today.minusDays(1);
        LocalDateTime day2 = today.minusDays(2);
        LocalDateTime day3 = today.minusDays(3);
        LocalDateTime day4 = today.minusDays(4);
        LocalDateTime day5 = today.minusDays(5);
        List<Long> list = new ArrayList<>();
        long l = findRecentEmoByDate(today);
        list.add(0, l);
        long l0 = findRecentEmoByDate(day1);
        list.add(0, l0);
        long l1 = findRecentEmoByDate(day2);
        list.add(0, l1);
        long l2 = findRecentEmoByDate(day3);
        list.add(0, l2);
        long l3 = findRecentEmoByDate(day4);
        list.add(0, l3);
        long l4 = findRecentEmoByDate(day5);
        list.add(0, l4);
        return list;
    }

    public long findRecentEmoByDate(LocalDateTime endDate) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("emoTime").lte(endDate)),
                Aggregation.group("type").count().as("totalEmoCount")  // Nhóm theo loại cảm xúc và tính tổng số lượng
        );
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "page_interactions", Document.class);
        List<Document> mappedResults = results.getMappedResults();

        long totalEmoCount = 0;
        for (Document document : mappedResults) {
            // Lấy tổng số lượng cảm xúc từ mỗi loại và cộng vào tổng số lượng tổng cộng
            totalEmoCount += document.getInteger("totalEmoCount");
        }
        return totalEmoCount;
    }

    @Override
    public List<Long> findRecentCommentByDate() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime day1 = today.minusDays(1);
        LocalDateTime day2 = today.minusDays(2);
        LocalDateTime day3 = today.minusDays(3);
        LocalDateTime day4 = today.minusDays(4);
        LocalDateTime day5 = today.minusDays(5);
        List<Long> list = new ArrayList<>();
        long l = findRecentCommentByDate(today);
        list.add(0, l);
        long l0 = findRecentCommentByDate(day1);
        list.add(0, l0);
        long l1 = findRecentCommentByDate(day2);
        list.add(0, l1);
        long l2 = findRecentCommentByDate(day3);
        list.add(0, l2);
        long l3 = findRecentCommentByDate(day4);
        list.add(0, l3);
        long l4 = findRecentCommentByDate(day5);
        list.add(0, l4);
        return list;
    }

    public long findRecentCommentByDate(LocalDateTime endDate) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("createAt").lte(endDate).and("type").is("COMMENT")),
                Aggregation.group("page_id").count().as("totalCommentCount")  // Nhóm theo loại cảm xúc và tính tổng số lượng
        );
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "comments", Document.class);
        List<Document> mappedResults = results.getMappedResults();

        long totalEmoCount = 0;
        for (Document document : mappedResults) {
            // Lấy tổng số lượng cảm xúc từ mỗi loại và cộng vào tổng số lượng tổng cộng
            totalEmoCount += document.getInteger("totalCommentCount");
        }
        return totalEmoCount;
    }

    @Override
    public List<Long> findRecentRateByDate() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime day1 = today.minusDays(1);
        LocalDateTime day2 = today.minusDays(2);
        LocalDateTime day3 = today.minusDays(3);
        LocalDateTime day4 = today.minusDays(4);
        LocalDateTime day5 = today.minusDays(5);
        List<Long> list = new ArrayList<>();
        long l = findRecentRateByDate(today);
        list.add(0, l);
        long l0 = findRecentRateByDate(day1);
        list.add(0, l0);
        long l1 = findRecentRateByDate(day2);
        list.add(0, l1);
        long l2 = findRecentRateByDate(day3);
        list.add(0, l2);
        long l3 = findRecentRateByDate(day4);
        list.add(0, l3);
        long l4 = findRecentRateByDate(day5);
        list.add(0, l4);
        return list;
    }

    public long findRecentRateByDate(LocalDateTime endDate) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("createAt").lte(endDate).and("type").is("RATE")),
                Aggregation.group("book_id").count().as("totalRateCount")  // Nhóm theo loại cảm xúc và tính tổng số lượng
        );
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "comments", Document.class);
        List<Document> mappedResults = results.getMappedResults();

        long totalEmoCount = 0;
        for (Document document : mappedResults) {
            // Lấy tổng số lượng cảm xúc từ mỗi loại và cộng vào tổng số lượng tổng cộng
            totalEmoCount += document.getInteger("totalRateCount");
        }
        return totalEmoCount;
    }

    public long findRecentReadByDate(LocalDateTime endDate) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("readTime").lte(endDate)),
                Aggregation.group("page_id.book_id").sum("read").as("totalReadCount"),
                Aggregation.sort(Sort.by("totalReadCount").descending())
        );
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "page_interactions", Document.class);
        List<Document> mappedResults = results.getMappedResults();
        long totalReadCount = 0;
        for (Document document : mappedResults) {
            if (document.containsKey("totalReadCount")) {
                totalReadCount += document.getInteger("totalReadCount");
            }
        }
        return totalReadCount;
    }


    @Override
    public List<Book> findComputedByLove() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("book_id").sum("love").as("totalLove"),
                Aggregation.sort(Sort.by("totalLove").descending())
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "computed_books", Map.class);
        List<Book> list = new ArrayList<>();
        for (Map map : results) {
            ObjectId objectId = (ObjectId) map.get("_id");
            String bookId = objectId.toHexString();
            Integer totalReadCount = Integer.parseInt(map.get("totalLove").toString());
            Optional<Book> b = bookRepository.findById(bookId);
            if (b.isPresent()) {
                Book bb = b.get();
                BookComputed computed = new BookComputed();
                computed.setLove(totalReadCount);
                bb.setBookComputed(computed);
                list.add(bb);
            }
        }
        return list;
    }

    @Override
    public List<Book> findComputedByComment() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("book_id").sum("commentCount").as("totalComment"),
                Aggregation.sort(Sort.by("totalComment").descending())
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "computed_books", Map.class);
        List<Book> list = new ArrayList<>();
        for (Map map : results) {
            ObjectId objectId = (ObjectId) map.get("_id");
            String bookId = objectId.toHexString();
            Integer totalReadCount = Integer.parseInt(map.get("totalComment").toString());
            Optional<Book> b = bookRepository.findById(bookId);
            if (b.isPresent()) {
                Book bb = b.get();
                BookComputed computed = new BookComputed();
                computed.setComment(totalReadCount);
                bb.setBookComputed(computed);
                list.add(bb);
            }
        }
        return list;
    }

    @Override
    public List<Book> findComputedByRate() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("reviewCount").gt(0)),
                Aggregation.group("book_id").first("totalRate").as("totalRate"), // Get the first totalRate for each book
                Aggregation.sort(Sort.by("totalRate").descending())  // Sort by totalRate (descending)
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "computed_books", Map.class);
        List<Book> list = new ArrayList<>();
        for (Map map : results) {
            ObjectId objectId = (ObjectId) map.get("_id");
            String bookId = objectId.toHexString();
            Double totalReadCount = (Double) map.get("totalRate");
            Optional<Book> b = bookRepository.findById(bookId);
            if (b.isPresent()) {
                Book bb = b.get();
                BookComputed computed = new BookComputed();
                computed.setRate(totalReadCount);
                bb.setBookComputed(computed);
                list.add(bb);
            }
        }
        return list;
    }

    @Override
    public List<Book> findComputedByRateCount() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("book_id").sum("reviewCount").as("totalReviewCount"),
                Aggregation.sort(Sort.by("totalReviewCount").descending())
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "computed_books", Map.class);
        List<Book> list = new ArrayList<>();
        for (Map map : results) {
            ObjectId objectId = (ObjectId) map.get("_id");
            String bookId = objectId.toHexString();
            Integer totalReadCount = Integer.parseInt(map.get("totalReviewCount").toString());
            Optional<Book> b = bookRepository.findById(bookId);
            if (b.isPresent()) {
                Book bb = b.get();
                BookComputed computed = new BookComputed();
                computed.setRateCount(totalReadCount);
                bb.setBookComputed(computed);
                list.add(bb);
            }
        }
        return list;
    }

    @Override
    public List<Book> findComputedBySave() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("book_id").sum("save").as("totalSave"),
                Aggregation.sort(Sort.by("totalSave").descending())
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "computed_books", Map.class);
        List<Book> list = new ArrayList<>();
        for (Map map : results) {
            ObjectId objectId = (ObjectId) map.get("_id");
            String bookId = objectId.toHexString();
            Integer totalReadCount = Integer.parseInt(map.get("totalSave").toString());
            Optional<Book> b = bookRepository.findById(bookId);
            if (b.isPresent()) {
                Book bb = b.get();
                BookComputed computed = new BookComputed();
                computed.setSave(totalReadCount);
                bb.setBookComputed(computed);
                list.add(bb);
            }
        }
        return list;
    }

    private List<Book> findRecentReadsByPage(LocalDateTime startDate, LocalDateTime endDate) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("readTime").gte(startDate).lte(endDate)),
                Aggregation.group("page_id.book_id").sum("read").as("totalReadCount"),
                Aggregation.sort(Sort.by("totalReadCount").descending())
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "page_interactions", Map.class);
        List<Book> list = new ArrayList<>();
        for (Map map : results) {
            ObjectId objectId = (ObjectId) map.get("_id");
            String bookId = objectId.toHexString();
            Integer totalReadCount = (Integer) map.get("totalReadCount");
            Optional<Book> b = bookRepository.findById(bookId);
            if (b.isPresent()) {
                Book bb = b.get();
                BookComputed computed = new BookComputed();
                computed.setRead(totalReadCount);
                bb.setBookComputed(computed);
                list.add(bb);
            }
        }
        return list;
    }

    private List<Book> findRecentReadsByPage() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("page_id.book_id").sum("read").as("totalReadCount"),
                Aggregation.sort(Sort.by("totalReadCount").descending())
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "page_interactions", Map.class);
        List<Book> list = new ArrayList<>();
        for (Map map : results) {
            ObjectId objectId = (ObjectId) map.get("_id");
            String bookId = objectId.toHexString();
            Integer totalReadCount = (Integer) map.get("totalReadCount");
            Optional<Book> b = bookRepository.findById(bookId);
            if (b.isPresent()) {
                Book bb = b.get();
                BookComputed computed = new BookComputed();
                computed.setRead(totalReadCount);
                bb.setBookComputed(computed);
                list.add(bb);
            }
        }
        return list;
    }
}

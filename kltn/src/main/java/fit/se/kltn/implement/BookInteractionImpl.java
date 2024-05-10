package fit.se.kltn.implement;

import fit.se.kltn.dto.BookComputed;
import fit.se.kltn.entities.Book;
import fit.se.kltn.entities.BookInteraction;
import fit.se.kltn.repositoties.BookInteractionRepository;
import fit.se.kltn.repositoties.BookRepository;
import fit.se.kltn.services.BookInteractionService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

@Component
@Slf4j
public class BookInteractionImpl implements BookInteractionService {
    @Autowired
    private BookInteractionRepository repository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookInteraction> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "nominalTime"));
    }
    @Override
    public Map<Book, List<BookInteraction>> groupInteractionsByBookId(List<BookInteraction> interactions) {
        return interactions.stream()
                .collect(Collectors.groupingBy(BookInteraction::getBook));
    }
    @Override
    public List<Book> findRecentNominations(String period) {
        LocalDateTime today = LocalDateTime.now();
        switch (period) {
            case "day":
                LocalDateTime oneDay = today.minusDays(1);
                return findRecentNominationsWithCounts(oneDay, today);
            case "week":
                LocalDateTime oneWeekAgo = today.minusDays(7);
                return findRecentNominationsWithCounts(oneWeekAgo, today);
            case "month":
                LocalDateTime oneMonthAgo = today.minusMonths(1);
                return findRecentNominationsWithCounts(oneMonthAgo, today);
            case "total":
                return findRecentNominationsWithCounts();
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }
    @Autowired
    private MongoTemplate mongoTemplate; // Inject MongoTemplate

    public List<Book> findRecentNominationsWithCounts(LocalDateTime startDate, LocalDateTime endDate) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("nominalTime").gte(startDate).lte(endDate).and("nominated").is(true)),
                Aggregation.group("book_id").count().as("nominatedCount"),
                Aggregation.sort(Sort.by("nominatedCount").descending())
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "book_interactions", Map.class);
        List<Map> mappedResults = results.getMappedResults();
       List<Book> list= new ArrayList<>();
        for (Map map : mappedResults) {
            ObjectId objectId = (ObjectId) map.get("_id");
            String bookId =objectId.toHexString();
            Integer nominatedCount = (Integer) map.get("nominatedCount");
            Optional<Book> b = bookRepository.findById(bookId);
            if(b.isPresent()){
                Book bb = b.get();
                BookComputed computed= new BookComputed();
                computed.setNominated(nominatedCount);
                bb.setBookComputed(computed);
                list.add(bb);
            }
        }
        return list;
    }

    public List<Book> findRecentNominationsWithCounts() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("nominated").is(true)),
                Aggregation.group("book_id").count().as("nominatedCount"),
                Aggregation.sort(Sort.by("nominatedCount").descending())
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "book_interactions", Map.class);
        List<Map> mappedResults = results.getMappedResults();
        List<Book> list= new ArrayList<>();
        for (Map map : mappedResults) {
            ObjectId objectId = (ObjectId) map.get("_id");
            String bookId =objectId.toHexString();
            Integer nominatedCount = (Integer) map.get("nominatedCount");
            Optional<Book> b = bookRepository.findById(bookId);
            if(b.isPresent()){
                Book bb = b.get();
                BookComputed computed= new BookComputed();
                computed.setNominated(nominatedCount);
                bb.setBookComputed(computed);
                list.add(bb);
            }
        }
        return list;
    }

    @Override
    public BookInteraction save(BookInteraction bookInteraction) {
        return repository.save(bookInteraction);
    }

    @Override
    public Optional<BookInteraction> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<BookInteraction> getBookInteraction(String bookId, String profileId) {
        return repository.findByBook_IdAndProfile_Id(bookId, profileId);
    }

    @Override
    public List<BookInteraction> findByBookId(String bookId) {
        return repository.findByBook_Id(bookId);
    }

    @Override
    public List<BookInteraction> findByProfileId(String profileId) {
        return repository.findByProfile_Id(profileId);
    }
}

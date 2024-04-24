package fit.se.kltn;

import fit.se.kltn.entities.*;
import fit.se.kltn.enums.BookStatus;
import fit.se.kltn.enums.ERole;
import fit.se.kltn.enums.UserStatus;
import fit.se.kltn.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableMongoRepositories
public class KltnApplication {
    public static void main(String[] args) {
        SpringApplication.run(KltnApplication.class, args);
    }

    //	@Qualifier("userServiceImpl")
//	@Autowired
//	private UserService service;
//	@Bean
//	CommandLineRunner init(){
//		return new CommandLineRunner() {
//			@Override
//			public void run(String... args) throws Exception {
//				User u= new User("20103091","Manh@2002", ERole.USER ,UserStatus.ACTIVE);
//				service.save(u);
//
//			}
//		};
//	}

    @Autowired
    private GenreService genreService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private PageService pageService;
    @Autowired
    private BookService bookService;

//    @Bean
    CommandLineRunner init() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book b = bookService.findById("6602da6d23a4f271fe9056f7").get();
                PageBook pageBook = new PageBook("", 1, "", b);
                PageBook pageBook2 = new PageBook("", 2, "", b);
                PageBook p1 = pageService.save(pageBook);
                PageBook p2 = pageService.save(pageBook2);
                b.setPages(List.of(p1, p2));
                bookService.save(b);
            }
        };
    }
//        @Bean
//    CommandLineRunner init() {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                Author author = new Author("Viktor E. Frankl");
//                Author author2 = new Author("Baird Thomas Spalding");
//                Author author3 = new Author("J.K. Rowling");
//                Author author4 = new Author("C.S. Lewis");
//                Author author5 = new Author("J.R.R. Tolkien");
//                Author author6 = new Author("Jack Canfield");
//                Author author7 = new Author("First News");
//                Author author8 = new Author("Robin Sharma");
//                Author author9 = new Author("Fyodor Dostoevsky");
//
//                Author a = authorService.save(author);
//                Author a2 = authorService.save(author2);
//                Author a3 = authorService.save(author3);
//                Author a4 = authorService.save(author4);
//                Author a5 = authorService.save(author5);
//                Author a6 = authorService.save(author6);
//                Author a7 = authorService.save(author7);
//                Author a8 = authorService.save(author8);
//                Author a9 = authorService.save(author9);
//                Genre genre = new Genre("Tâm lý - Tự viện");
//                Genre genre2 = new Genre("Triết Học");
//                Genre genre3 = new Genre("Tự Truyện");
//                Genre genre4 = new Genre("Kinh Điển");
//                Genre genre5 = new Genre("triết lý");
//                Genre genre6 = new Genre("Phát Triển Bản Thân");
//                Genre genre7 = new Genre("Giả Tưởng");
//                Genre genre8 = new Genre("Ma Pháp");
//                Genre genre9 = new Genre("Giáo dục");
//                Genre genre10 = new Genre("Tôn giáo");
//                Genre genre11 = new Genre("Tiểu Thuyết");
//                Genre genre12 = new Genre("Văn Học - Sử Thi");
//                Genre genre13 = new Genre("Văn Học");
//                Genre genre14 = new Genre("Tâm Lý - Tự Lực");
//                Genre genre15 = new Genre("Tự Lực");
//                Genre genre16 = new Genre("Cảm Hứng");
//                Genre genre17 = new Genre("Danh tác");
//                Genre genre18 = new Genre("Tâm Lý");
//                Genre genre19 = new Genre("Kinh Điển");
//                Genre genre20 = new Genre("Phiêu lưu");
//                Genre g = genreService.save(genre);
//                Genre g2 = genreService.save(genre2);
//                Genre g3 = genreService.save(genre3);
//                Genre g4 = genreService.save(genre4);
//                Genre g5 = genreService.save(genre5);
//                Genre g6 = genreService.save(genre6);
//                Genre g7 = genreService.save(genre7);
//                Genre g8 = genreService.save(genre8);
//                Genre g9 = genreService.save(genre9);
//                Genre g10 = genreService.save(genre10);
//                Genre g11 = genreService.save(genre11);
//                Genre g12 = genreService.save(genre12);
//                Genre g13 = genreService.save(genre13);
//                Genre g14 = genreService.save(genre14);
//                Genre g15 = genreService.save(genre15);
//                Genre g16 = genreService.save(genre16);
//                Genre g17 = genreService.save(genre17);
//                Genre g18 = genreService.save(genre18);
//                Genre g19 = genreService.save(genre19);
//                Genre g20 = genreService.save(genre20);
//                Book b = bookService.findById("6602da6d23a4f271fe9056f7").get();
//                b.setAuthors(List.of(a));
//                b.setGenres(List.of(g, g2, g3, g4));
//                bookService.save(b);
//                Book b2 = bookService.findById("6602da7523a4f271fe9056f8").get();
//                b2.setAuthors(List.of(a2));
//                b2.setGenres(List.of(g, g5, g6));
//                bookService.save(b2);
//                Book b3 = bookService.findById("6602daa923a4f271fe9056f9").get();
//                b3.setAuthors(List.of(a3));
//                b3.setGenres(List.of(g7, g8, g9, g10));
//                bookService.save(b3);
//                Book b4 = bookService.findById("6602dac223a4f271fe9056fa").get();
//                b4.setAuthors(List.of(a4));
//                b4.setGenres(List.of(g7, g8, g11, g12, g20));
//                PageBook pageBook = new PageBook("", 1, "Chiến tranh thế giới thứ hai bùng nổ, 4 anh em nhà Pevensie là Peter, Susan, Edmund và Lucy phải sơ tán đến ở nhà của giáo sư Digory Kirke. Một lần, khi 4 người đang chơi trốn tìm, Lucy phát hiện ra trong tủ quần áo một thế giới khác được gọi là Narnia, tại đó đang là mùa đông. Dưới chân một cột đèn, Lucy nhìn thấy một thần đồng áng. Ông giới thiệu mình tên là Tummus, và kể cho Lucy biết về thế giới này. Tummus đưa Lucy về nhà và đưa cô vào giấc ngủ bằng một điệu sáo. Khi tỉnh dậy, Lucy thấy Tummus đang rất buồn, ông giải thích cho cô rằng thế giới này đã bị Jadis, mụ phù thủy trắng nguyền rủa sẽ phải chịu đựng mùa đông trong 100 năm, và bất kì ai thấy một con người cũng sẽ phải bắt và giao nộp cho ả. Tummus không nỡ bắt Lucy nên đã đưa cô trở lại thế giới bình thường. Trở lại, Lucy kể cho các anh chị em câu chuyện đó nhưng không ai chịu tin cô.", b4);
//                PageBook pageBook2 = new PageBook("", 2, "Đến được trại của Aslan, Peter được phong tước hiện sĩ của Narnia. Đúng lúc này, phù thủy trắng xuất hiện và nói Edmund là kẻ phản bội, sẽ phải chịu hình phạt tử hình. Aslan quyết định hy sinh thân mình để cứu Edmund. Đêm đó, Susan và Lucy bí mật đến bàn đá nơi Aslan bị xử tử. Buổi sáng hôm sau, ông bất ngờ được hồi sinh bởi một ma thuật kì diệu. Aslan đưa Susan và Lucy đến lâu đài phù thủy, nơi ông giải phóng các tù nhân đã bi mụ hóa thành đá.\n" +
//                        "Trong khi đó, một cuộc chiến ác liệt đang diễn ra giữa quân đội của Aslan và quân của mụ phù thủy. Edmund vì cứu Peter mà bị thương nặng. Khi quân phù thủy đang áp đảo thì Aslan quay lại với đội quân tiếp viện và giết chết mụ phù thủy. Edmund cũng được hồi sinh bởi lọ nước thần mà ông già Noel tặng cho Lucy. Bốn người trở thành vua và nữ hoàng của Narnia.\n" +
//                        "Mười lăm năm sau, trong một lần truy đuổi một con hươu trắng, 4 anh em nhìn thấy cái cột đèn mà Lucy đã thấy trong lần đầu tiên đến Narnia. Họ ngược trở lại, đi qua những bụi cây, tủ quần áo và bất ngờ trở lại thế giới hiện tại vẫn với hình hài của những đứa trẻ. Lucy sau đó cố gắng quay trở lại Narnia bằng tủ quần áo nhưng vô vọng, và cô được giáo sư Kirke cho biết cô có thể quay trở lại nó vào lúc cô ít ngờ tới nhất.", b4);
//                PageBook p1 = pageService.save(pageBook);
//                PageBook p2 = pageService.save(pageBook2);
//                b4.setPages(List.of(p1, p2));
//                bookService.save(b4);
//                Book b5 = bookService.findById("6602dae623a4f271fe9056fc").get();
//                b5.setAuthors(List.of(a5));
//                b5.setGenres(List.of(g7, g11, g13));
//                bookService.save(b5);
//                Book b6 = bookService.findById("6602db1e23a4f271fe9056fd").get();
//                b6.setAuthors(List.of(a6, a7));
//                b6.setGenres(List.of(g6, g14));
//                bookService.save(b6);
//                Book b7 = bookService.findById("6602dcbb23a4f271fe9056fe").get();
//                b7.setAuthors(List.of(a8));
//                b7.setGenres(List.of(g6, g15, g16));
//                bookService.save(b7);
//                Book b8 = bookService.findById("6602df4923a4f271fe9056ff").get();
//                b8.setAuthors(List.of(a9));
//                b8.setGenres(List.of(g2, g11, g17, g18, g19));
//                bookService.save(b8);
//            }
//        };
//    }


}

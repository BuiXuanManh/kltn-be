package fit.se.kltn;

import fit.se.kltn.entities.*;
import fit.se.kltn.enums.BookStatus;
import fit.se.kltn.enums.ERole;
import fit.se.kltn.enums.RateType;
import fit.se.kltn.enums.UserStatus;
import fit.se.kltn.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableMongoRepositories
public class KltnApplication {
    public static void main(String[] args) {
        SpringApplication.run(KltnApplication.class, args);
    }

//    @Qualifier("userServiceImpl")
//    @Autowired
//    private UserService service;
//    @Autowired
//    private ProfileService profileService;
//    @Autowired
//    private BookService bookService;
//    @Qualifier("bookInteractionImpl")
//    @Autowired
//    private BookInteractionService interactionService;
//    @Autowired
//    private PageService pageService;
//    @Autowired
//    private CommentService commentService;
//    @Qualifier("pageInteractionImpl")
//    @Autowired
//    private PageInteractionService interactionService;

//    @Bean
//CommandLineRunner init() {
//    return new CommandLineRunner() {
//        @Override
//        public void run(String... args) throws Exception {
//            PageBook page=pageService.findById("662890319c3cb5741b7de434").get();
//            Profile p= profileService.findById("6613fa53dba2361c3d3eb049").get();
//            Comment comment= new Comment();
//            comment.setType(RateType.COMMENT);
//            comment.setCreateAt(LocalDateTime.now());
//            comment.setContent("sách hay");
//            comment.setProfile(p);
//            comment.setPageBook(page);
//            Comment c = commentService.save(comment);
//            PageInteraction interaction= new PageInteraction();
//            interaction.setProfile(p);
//            interaction.setPageBook(page);
//            interaction.setReadTime(LocalDateTime.now());
//            interactionService.save(interaction);
//        }
//    };
//}

    //    @Bean
//    CommandLineRunner init() {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//
//                Book b= bookService.findById("6602da6d23a4f271fe9056f7").get();
//                Book b2= bookService.findById("6602da7523a4f271fe9056f8").get();
//                Book b3= bookService.findById("6602daa923a4f271fe9056f9").get();
//                Book b4= bookService.findById("6602dac223a4f271fe9056fa").get();
//                Book b5= bookService.findById("6602dae623a4f271fe9056fc").get();
//                Book b6= bookService.findById("6602db1e23a4f271fe9056fd").get();
//                Book b7= bookService.findById("6602dcbb23a4f271fe9056fe").get();
//                Book b8= bookService.findById("6602df4923a4f271fe9056ff").get();
//                b8.setCreatedAt(LocalDateTime.now());
//                b8.setUpdateDate(LocalDateTime.now());
//                bookService.save(b8);
//            }
//        };
//    }
//
//    	@Bean
//    CommandLineRunner init() {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                Profile p = profileService.findById("6613fa53dba2361c3d3eb049").get();
//                Book b= bookService.findById("6602da6d23a4f271fe9056f7").get();
//                Book b2= bookService.findById("6602daa923a4f271fe9056f9").get();
//                Book b3= bookService.findById("6602db1e23a4f271fe9056fd").get();
//
//                BookInteraction bi1= new BookInteraction(b,p,2);
//
//                BookInteraction bi2= new BookInteraction(b2,p,2);
//                BookInteraction bi3= new BookInteraction(b3,p,3);
//                BookInteraction in = interactionService.save(bi1);
//                BookInteraction in2=interactionService.save(bi2);
//                BookInteraction in3=interactionService.save(bi3);
////                List<BookInteraction> l= interactionService.findAll();
//                b.setInteractions(List.of(in));
//                b2.setInteractions(List.of(in2));
//                b3.setInteractions(List.of(in3));
//                bookService.save(b);
//                bookService.save(b2);
//                bookService.save(b3);
//
//                p.setInteractions(List.of(in,in2,in3));
//                profileService.save(p);
//            }
//        };
//    }

//    @Autowired
//    private GenreService genreService;
//    @Autowired
//    private AuthorService authorService;
//    @Autowired
//    private PageService pageService;


//    @Bean
//    CommandLineRunner init() {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                Book b = bookService.findById("6602df4923a4f271fe9056ff").get();
//                PageBook pageBook= new PageBook("",1,"Tác phẩm tập trung vào nhân vật trung tâm Rodion Romanovich Raskolnikov, một sinh viên trường luật ở Saint Petersburg. Raskolnikov xuất thân từ một gia đình nghèo ở nông thôn, người mẹ không đủ điều kiện nuôi anh ăn học đến ngày thành đạt, cô em gái Dunhia giàu lòng hy sinh phải làm gia sư cho gia đình lão địa chủ quý tộc dâm dục Arkady Ivanovich Svidrigailov để nuôi anh. Nhưng vốn là một cô gái thông minh, giàu tự trọng, Dunhia bỏ việc dạy học vì bị lão địa chủ Svidrigailov ve vãn hòng chiếm đoạt, mặc dù lão đã có vợ con. Đời sống gia đình ngày càng khó khăn khiến Raskolnikov phải bỏ học giữa chừng. Trong hoàn cảnh khó khăn như vậy, một người mối lái đưa Pyotr Petrovich Luzhin (Luzhin), một viên quan cao cấp ngành Toà án ở Thủ đô đến gặp Dunhia hỏi vợ.\n" +
//                        "\n" +
//                        "Do đời sống thiếu thốn, hàng ngày chứng kiến nơi cái xóm trọ toàn dân nghèo với bao cảnh đời lầm than, cùng quẫn, lại bị tiêm nhiễm bởi triết lý người hùng khi mơ tưởng một ngày sẽ được như Napoléon Bonaparte, Raskolnikov tự coi mình là phi thường, thường xuyên khép kín lòng mình, bơi mải miết trong những suy tư đơn độc, nung nấu những căm uất về tình trạng bất công, phi nghĩa của xã hội và tìm kiếm lối thoát bằng sức lực của cá nhân mình. Những lý do đó đã khiến chàng, trong một lần nọ, đã quyết định đến nhà mụ cầm đồ Alyona Ivanovna giàu nứt đố đổ vách, lạnh lùng lấy búa bổ vỡ đầu mụ và cướp tiền bạc, châu báu. Sau khi mở được két tiền, quay ra chàng gặp ngay em gái mụ cầm đồ, Elizabet. Vì quá hốt hoảng Raskolnikov vung rìu đập chết luôn ả. Trốn khỏi căn nhà mụ cầm đồ, chàng giấu kín gói đồ cướp được dưới một tảng đá và không dám tiêu một đồng mặc dù không còn một xu dính túi. Sau vụ giết người khủng khiếp đó, dù chưa bị phát hiện, lương tâm của Raskolnikov vẫn bị dày vò triền miên. Chàng như người mất hồn tâm thần hoảng loạn, đêm nằm mê sảng vật vã, ngày đi lang thang vơ vẩn.\n" +
//                        "\n" +
//                        "Trong một hôm uống rượu giải khuây, trong quán tình cờ Raskolnikov tâm sự với một bác công nhân già nát rượu Semyon Zakharovich Marmeladov và biết được Sonya, con gái bác phải bán thân để nuôi cả cha, mẹ kế và các em trong khung cảnh đói rét và bệnh tật. Raskolnikov đã đến với Sonya để rồi tình cảm giữa chàng và Sonya ngày càng gắn bó.\n" +
//                        "\n" +
//                        "Trong lúc đó, Dunhia tuy chưa rõ Luzhin là kẻ tốt hay xấu, nhưng cả mẹ và nàng đều tìm thấy ở con người có thế lực giàu sang này chỗ dựa chắc chắn về kinh tế, không chỉ giúp gia đình mà cả con đường công danh của Raskolnikov về sau. Thương anh và thương mẹ, Dunhia đã nhận lời đính hôn với Luzhin, người có thể trở thành chỗ dựa kinh tế cho gia đình nàng, đồng ý cùng mẹ về sống ở thủ đô để chuẩn bị lễ cưới. Biết chuyện Raskolnikov ra sức chống lại đám cưới của Dunhia và Luzhin, vì chàng hiểu rõ bản chất đồi bại bỉ ổi của kẻ tai to mặt lớn này trong giai đoạn chàng còn ở St. Petersburg. Chàng cho rằng nếu để em gái Dunhia cưới Luzhin thì không khác nào đồng ý cho Dunhia đi làm gái mại dâm như số phận của Sonya. Như vậy là phạm tội ác đến hai lần, không chỉ giết chết nhân phẩm của Dunhia mà còn giết chết cả nhân phẩm của chính mình. Raskolnikov đã không ngần ngại đuổi Luzhin ra khỏi nhà ngay trước mặt mẹ và em gái.\n" +
//                        "\n" +
//                        "Đang đi lang thang trên phố, thấy bác công nhân Marmeladov nát rượu bị xe ngựa cán ngã lăn ra đường mê man bất tỉnh, Raskonikov đã vội vàng đưa bác về nhà, rồi tự tay bỏ tiền ra lo việc ma chay cho gia đình của Sonya. Từ đó tình yêu giữa chàng và Sonya ngày càng thắm thiết.",b);
//                PageBook pageBook2= new PageBook("",2,"Luzhin, với bản chất xấu xa của hắn, không quên mối nhục và tìm cách trả thù Raskolnikov. Nhân lúc nhà Sonya có tang, hắn giả vờ xót thương gọi Sonya tới nhà mình và cho nàng 10 ruble nhưng lại lén bỏ vào trong túi nàng một tờ 100 ruble. Sau đó, hắn đến đám tang, đột ngột bước vào không thèm chào hỏi ai và đến trước mặt bà mẹ góa kêu ầm lên là mất tờ 100 ruble hắn để trên bàn vào lúc Sonya tới nhà hắn. Hắn cả quyết rằng chỉ có Sonya lấy cắp, đòi khám áo Sonya và thấy quả là từ đáy túi áo ngoài của nàng rơi ra một tờ giấy 100 ruble được gấp làm tám. Luzhin la toáng lên yêu cầu gọi cảnh sát đến bắt Sonya. Mục đích của hắn nhằm bôi xấu Raskolnikov và cứu vãn danh dự của bản thân: sở dĩ hắn không kết hôn được với Dunhia bởi ông anh trai nàng có người tình là kẻ ăn cắp. Nhưng trong lúc hắn đang hí hửng vì hạ nhục được Raskolnikov trước đông đảo mọi người, thì bạn của hắn, trước đó vô tình đứng ngoài cửa đã chứng kiến Luzhin bỏ tờ giấy bạc 100 ruble vào túi Sonya, tưởng Luzhin cho tiền để giúp đỡ Sonya. Anh bạn đã vạch mặt trò bịp này của Luzhin và không thể chối cãi, Luzhin bẽ mặt lủi thủi ra về.\n" +
//                        "\n" +
//                        "Raskolnikov vẫn triền miên trong nỗi ân hận dày vò vì đã giết người cướp của. Tâm trí chàng luôn căng thẳng, vừa vì sự lẩn tránh tội lỗi, vừa vì những dằn vặt ám ảnh của bản thân khiến toàn thân chàng nhiều lúc rã rời, đầu óc muốn nổ tung, và đã tâm sự với Sonya rằng anh giết người bởi muốn trở thành một Napoléon Bonaparte (?). Trong một lần tự đối thoại với chính mình, chàng đã liên tục tự hỏi \"ta là con sâu con bọ run rẩy hay ta có quyền lực?\", và khi hiểu ra phần nào chàng đã thốt lên \"ta đã giết không phải một con người, ta đã giết một nguyên lý\". Với chàng lúc này hình phạt ghê gớm nhất không phải là tù đày mà là nỗi nhức nhối dai dẳng vì đã giết chết nhân phẩm của mình và cắt đứt quan hệ với những người thân thiết.\n" +
//                        "\n" +
//                        "Dunhia hết sức đau khổ vì sự cùng quẫn của anh trai nhưng vẫn không thể tìm lối thoát. Trong lúc đó lão địa chủ Svidrigailov vẫn không từ bỏ ý định theo đuổi nàng, thậm chí hắn đã giết cả vợ. Song hắn không thể chinh phục nổi người con gái trong sáng và nghị lực ấy. Vốn là kẻ giàu có lại sống trụy lạc, hắn định kết hôn với một cô gái rất trẻ, con một quý tộc bị phá sản đang cần nơi nương tựa. Nhưng rồi trong một cơn khủng hoảng, bất ngờ hắn đã rút súng lục tự tử ngay gần bốt cảnh sát, để lại một bức thư tuyệt mệnh xác nhận rằng hắn tự tìm đến cái chết. Trước đó hắn đã vĩnh biệt Sonya, người sống cạnh buồng trọ của hắn, và tặng nàng số tiền 3000 ruble để giúp đỡ gia đình nàng sinh sống. Còn Raskolnikov sau chín tháng dằn vặt đã đến tòa tự thú. Xét thấy thần kinh của chàng không ổn định, trước vành móng ngựa tòa miễn tội chết cho Raskolnikov và đày chàng biệt xứ ở Siberia trong 8 năm khổ sai. Sonya, người con gái đau khổ với trái tim tràn ngập bác ái đã tự nguyện gắn bó đời mình với người yêu nơi đày ải khắc nghiệt ấy.",b);
//                PageBook pageBook2= new PageBook("",1,"",b);
//                PageBook p1 = pageService.save(pageBook);
//                PageBook p2 = pageService.save(pageBook2);
//                PageBook p3 = pageService.save(pageBook3);
//                PageBook p4 = pageService.save(pageBook4);
//                PageBook p5 = pageService.save(pageBook5);
//                PageBook p6 = pageService.save(pageBook6);
//                PageBook p7 = pageService.save(pageBook7);
//                PageBook p8 = pageService.save(pageBook8);
//                b.setPages(List.of(p1, p2));
//                bookService.save(b);
//            }
//        };
//    }

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

package fit.se.kltn;

import fit.se.kltn.entities.User;
import fit.se.kltn.enums.ERole;
import fit.se.kltn.enums.UserStatus;
import fit.se.kltn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

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
//				User u= new User("user12345678","123456", ERole.USER ,UserStatus.ACTIVE);
//				service.save(u);
//			}
//		};
//	}

}

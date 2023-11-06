package online.book.store;

import java.math.BigDecimal;
import online.book.store.model.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineBookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setDescription("descr");
                book.setIsbn("isbn");
                book.setTitle("title");
                book.setPrice(BigDecimal.valueOf(6));
            }
        };
    }
}

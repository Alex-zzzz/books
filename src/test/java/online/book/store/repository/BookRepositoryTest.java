package online.book.store.repository;

import java.math.BigDecimal;
import java.util.List;
import online.book.store.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findAll() {
        Book book = new Book();
        book.setTitle("title");
        book.setIsbn("isbn6");
        book.setAuthor("author");
        book.setPrice(BigDecimal.valueOf(100));
        bookRepository.save(book);
        List<Book> actual = bookRepository.findAll();
        Assertions.assertEquals(4, actual.size());
    }
}

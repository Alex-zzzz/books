package online.book.store.service;

import java.util.List;
import online.book.store.model.Book;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}

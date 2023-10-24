package online.book.store.repository;

import java.util.List;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.model.Book;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();

    public Book getBookById(@PathVariable Long id) throws EntityNotFoundException;
}

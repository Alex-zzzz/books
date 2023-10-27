package online.book.store.service;

import java.util.List;
import java.util.Optional;
import online.book.store.dto.BookDto;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    Optional<Book> getBookById(Long id);

    Book updateBook(Long id, Book book);

    void delete(@PathVariable Long id);
}

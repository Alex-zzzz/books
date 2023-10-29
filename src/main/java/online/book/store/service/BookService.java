package online.book.store.service;

import java.util.List;
import online.book.store.dto.BookDto;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    Book getBookById(Long id) throws EntityNotFoundException;

    BookDto updateBook(Long id, CreateBookRequestDto bookDto);

    void delete(@PathVariable Long id) throws EntityNotFoundException;
}

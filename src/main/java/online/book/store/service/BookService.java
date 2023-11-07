package online.book.store.service;

import java.util.List;
import online.book.store.dto.BookDto;
import online.book.store.dto.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    BookDto getBookById(Long id);

    List<BookDto> getAll(Pageable pageable);

    BookDto updateBookById(Long id, CreateBookRequestDto updateDto);

    void deleteBookById(Long id);

}

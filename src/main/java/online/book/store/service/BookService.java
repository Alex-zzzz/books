package online.book.store.service;

import java.util.List;
import online.book.store.dto.BookDto;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto getBookById(Long id) throws EntityNotFoundException;
}

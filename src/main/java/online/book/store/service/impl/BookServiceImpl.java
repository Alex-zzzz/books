package online.book.store.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.BookDto;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.mapper.BookMapper;
import online.book.store.model.Book;
import online.book.store.repository.BookRepository;
import online.book.store.service.BookService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) throws EntityNotFoundException {
        return bookMapper.toDto(bookRepository.getBookById(id));
    }
}

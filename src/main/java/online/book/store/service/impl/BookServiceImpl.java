package online.book.store.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.BookDto;
import online.book.store.dto.BookSearchParametersDto;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.mapper.BookMapper;
import online.book.store.model.Book;
import online.book.store.model.Category;
import online.book.store.repository.BookRepository;
import online.book.store.repository.BookSpecificationBuilder;
import online.book.store.repository.CategoryRepository;
import online.book.store.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto request) {
        Book book = bookMapper.toEntity(request);
        book.setCategories(getCategoriesByIds(request.getCategoriesIds()));
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't get book by id: " + id)));
    }

    @Override
    public List<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto updateBookById(Long id, CreateBookRequestDto updateDto) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Can't find book by id: " + id);
        }

        Set<Category> categoriesByIds = getCategoriesByIds(updateDto.getCategoriesIds());
        Book book = bookMapper.toEntity(updateDto);
        book.setId(id);
        book.setCategories(categoriesByIds);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> searchBooks(BookSearchParametersDto parametersDto, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(parametersDto);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    private Set<Category> getCategoriesByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            throw new EntityNotFoundException("Can't find categories");
        }

        return ids.stream()
                .map(categoryRepository::findById)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }
}

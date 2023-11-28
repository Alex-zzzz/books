package online.book.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import online.book.store.dto.BookDto;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.mapper.BookMapper;
import online.book.store.model.Book;
import online.book.store.repository.BookRepository;
import online.book.store.repository.CategoryRepository;
import online.book.store.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void save_ValidCreateBookRequestDto_ReturnsBookDto() {
        List<Long> list = new ArrayList<>();
        list.add(1L);

        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto();
        bookRequestDto.setPrice(BigDecimal.valueOf(10));
        bookRequestDto.setTitle("test_title");
        bookRequestDto.setAuthor("test_author");
        bookRequestDto.setIsbn("isbn");
        bookRequestDto.setCategoriesIds(list);

        Book book = new Book();
        book.setTitle(bookRequestDto.getTitle());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setPrice(bookRequestDto.getPrice());

        BookDto bookDto = new BookDto(1L,book.getTitle(),book
                .getAuthor(),book.getIsbn(),book.getPrice(),"description","image",list);

        when(bookMapper.toEntity(bookRequestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto savedBookDto = bookService.save(bookRequestDto);
        assertThat(savedBookDto).isEqualTo(bookDto);
    }

    @Test
    void getBook_WithNonExistingId_ShouldThrowException() {
        Long bookId = 100L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class,
                () -> bookService.getBookById(bookId));
        String expected = "Can't get book by id: " + bookId;
        String actual = exception.getMessage();
        assertEquals(expected,actual);
    }

    @Test
    void searchBook_WithExistingId() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("test");
        book.setIsbn("isbn");
        book.setAuthor("test");
        book.setPrice(BigDecimal.valueOf(5));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Book bookById = bookRepository.findById(bookId).get();
        String actual = bookById.getAuthor();
        String expected = book.getAuthor();
        assertEquals(expected,actual);
    }

    @Test
    void findAll_ValidPageable_ReturnsAllBooks() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("title");
        book.setPrice(BigDecimal.valueOf(100));
        List<Long> list = new ArrayList<>();
        list.add(1L);
        BookDto bookDto = new BookDto(book.getId(),book.getTitle(),"author","isbn",
                book.getPrice(),"","",list);
        Pageable pageable = PageRequest.of(0,10);
        List<Book> books = List.of(book);
        Page<Book> bookPage = new PageImpl<>(books,pageable,books.size());
        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        List<BookDto> bookDtos = bookService.getAll(pageable);
        assertThat(bookDtos).hasSize(1);
        assertThat(bookDtos.get(0)).isEqualTo(bookDto);
    }
}

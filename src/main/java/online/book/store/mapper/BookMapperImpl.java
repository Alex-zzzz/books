package online.book.store.mapper;

import online.book.store.dto.BookDto;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper {
    private BookDto dto = new BookDto();

    @Override
    public BookDto toDto(Book book) {
        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setDescription(book.getDescription());
        dto.setCoverImage(book.getCoverImage());
        dto.setAuthor(book.getAuthor());
        dto.setTitle(book.getTitle());
        return dto;
    }

    @Override
    public Book toModel(CreateBookRequestDto bookDto) {
        Book book = new Book();
        book.setPrice(bookDto.getPrice());
        book.setCoverImage(bookDto.getCoverImage());
        book.setDescription(bookDto.getDescription());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        return book;
    }
}

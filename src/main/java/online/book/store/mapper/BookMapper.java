package online.book.store.mapper;

import online.book.store.dto.BookDto;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.model.Book;

public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto bookDto);
}

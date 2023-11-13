package online.book.store.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import online.book.store.config.MapperConfig;
import online.book.store.dto.BookDto;
import online.book.store.dto.BookDtoWithoutCategoryIds;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.model.Book;
import online.book.store.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoriesIds", source = "categories", qualifiedByName = "setCategoryId")
    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Book toEntity(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @Named("setCategoryId")
    default List<Long> setCategoryId(Set<Category> categories) {
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toList());
    }
}

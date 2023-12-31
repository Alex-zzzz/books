package online.book.store.mapper;

import online.book.store.config.MapperConfig;
import online.book.store.dto.CategoryRequestDto;
import online.book.store.dto.CategoryResponseDto;
import online.book.store.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Category toEntity(CategoryRequestDto request);
}

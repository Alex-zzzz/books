package online.book.store.service;

import java.util.List;
import online.book.store.dto.CategoryRequestDto;
import online.book.store.dto.CategoryResponseDto;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryResponseDto save(CategoryRequestDto request);

    CategoryResponseDto getById(Long id);

    List<CategoryResponseDto> getAll(Pageable pageable);

    CategoryResponseDto update(Long id, CategoryRequestDto request);

    void deleteById(Long id);

}

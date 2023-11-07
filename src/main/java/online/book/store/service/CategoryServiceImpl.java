package online.book.store.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.CategoryRequestDto;
import online.book.store.dto.CategoryResponseDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.mapper.BookMapper;
import online.book.store.mapper.CategoryMapper;
import online.book.store.model.Category;
import online.book.store.repository.BookRepository;
import online.book.store.repository.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDto save(CategoryRequestDto request) {
        Category category = categoryMapper.toEntity(request);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id: " + id)
        ));
    }

    @Override
    public List<CategoryResponseDto> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryRequestDto request) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "It is not possible to update a category by id: " + id
            );
        }

        Category category = categoryMapper.toEntity(request);
        category.setId(id);
        return categoryMapper.toDto(categoryRepository.save(category));
    }



    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "It is not possible to delete a category by id: " + id
            );
        }
        categoryRepository.deleteById(id);
    }
}

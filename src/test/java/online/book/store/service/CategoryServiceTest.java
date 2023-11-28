package online.book.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import online.book.store.dto.CategoryRequestDto;
import online.book.store.dto.CategoryResponseDto;
import online.book.store.mapper.CategoryMapper;
import online.book.store.model.Category;
import online.book.store.repository.CategoryRepository;
import online.book.store.service.impl.CategoryServiceImpl;
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
class CategoryServiceTest {

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void save_ValidCreateCategoryRequestDto_ReturnsCategoryDto() {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("test_name");
        categoryRequestDto.setDescription("test_description");

        Category category = new Category();
        category.setName("test_name");
        category.setDescription("test_description");
        CategoryResponseDto categoryResponseDto =
                new CategoryResponseDto(category.getId(),category.getName(),category
                        .getDescription());

        when(categoryMapper.toEntity(categoryRequestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryResponseDto);
        CategoryResponseDto responseDto = categoryService.save(categoryRequestDto);
        assertThat(responseDto.name()).isEqualTo(categoryRequestDto.getName());
        assertThat(responseDto.description()).isEqualTo(categoryRequestDto.getDescription());
    }

    @Test
    void findAll_ValidPageable_ReturnsAllCategories() {
        Category category = new Category();
        category.setName("test_name");

        List<Long> list = new ArrayList<>();
        list.add(1L);

        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("test_name");
        categoryRequestDto.setDescription("test_description");

        CategoryResponseDto categoryResponseDto =
                new CategoryResponseDto(category.getId(),category.getName(),category
                        .getDescription());

        Pageable pageable = PageRequest.of(0,10);
        List<Category> categories = List.of(category);
        Page<Category> categoryPage = new PageImpl<>(categories,pageable,categories.size());

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(category)).thenReturn(categoryResponseDto);

        List<CategoryResponseDto> categoryRequestDtos = categoryService.getAll(pageable);
        assertThat(categoryRequestDtos).hasSize(1);
        assertThat(categoryRequestDtos.get(0)).isEqualTo(categoryResponseDto);
    }

    @Test
    void searchCategory_WithExistingId() {
        Category category = new Category();
        category.setName("test_name");
        category.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        Category categoryById = categoryRepository.findById(1L).get();
        String actual = category.getName();
        String expected = categoryById.getName();
        assertEquals(expected,actual);
    }

    @Test
    void getCategory_WithNonExistingId_ShouldThrowException() {
        Long categoryId = 100L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> categoryService
                .getById(categoryId));
        String expected = "Can't find category by id: " + categoryId;
        String actual = exception.getMessage();
        assertEquals(expected,actual);
    }
}

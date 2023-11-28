package online.book.store.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import online.book.store.dto.CategoryRequestDto;
import online.book.store.dto.CategoryResponseDto;
import online.book.store.service.CategoryService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();

    }

    @Before
    public void setUp() {
        insertCategoryTestData();
    }

    private void insertCategoryTestData() {
        CategoryRequestDto category1 = new CategoryRequestDto();
        CategoryRequestDto category2 = new CategoryRequestDto();
        category1.setName("name1");
        category2.setName("name2");
        saveCategory(category1);
        saveCategory(category2);
    }

    private CategoryResponseDto saveCategory(CategoryRequestDto categoryRequestDto) {
        return categoryService.save(categoryRequestDto);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getAllCategories() throws Exception {
        mockMvc.perform(get("/categories").param("page", "0").param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getCategoryById() throws Exception {
        Long categoryId = 1L;
        mockMvc.perform(get("/categories/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryId));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getBooksByCategoryId() throws Exception {
        insertCategoryTestData();
        Long categoryId = 1L;
        mockMvc.perform(get("/categories/{id}/books", categoryId))
                .andExpect(status().isOk());
    }
}

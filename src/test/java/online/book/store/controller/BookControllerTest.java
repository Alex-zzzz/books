package online.book.store.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import online.book.store.dto.BookDto;
import online.book.store.dto.CreateBookRequestDto;
import online.book.store.model.Book;
import online.book.store.repository.BookRepository;
import online.book.store.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Mock
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void createBook_ValidRequestDto_Success() throws Exception {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto();
        bookRequestDto.setPrice(BigDecimal.valueOf(10));
        bookRequestDto.setTitle("test_title");
        bookRequestDto.setAuthor("test_author");
        bookRequestDto.setIsbn("isbn45");
        bookRequestDto.setCategoriesIds(list);
        String jsonRequest = objectMapper.writeValueAsString(bookRequestDto);
        MvcResult result = mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.id());
        Assertions.assertEquals(bookRequestDto.getTitle(), actual.title());
        Assertions.assertEquals(bookRequestDto.getPrice(), actual.price());

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getAllBooks() throws Exception {
        insertTestData();
        Pageable pageable = PageRequest.of(0, 10);
        mockMvc.perform(get("/books").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[1].author").value("author1"));

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getBookById() throws Exception {
        insertTestData();
        Long bookId = 1L;
        BookDto mockBook = new BookDto(bookId, "title", "author", "isbn",
                BigDecimal.valueOf(100.0), "", "", List.of(1L, 2L));

        when(bookService.getBookById(bookId)).thenReturn(mockBook);

        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.author").value("author"))
                .andExpect(jsonPath("$.isbn").value("isbn"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.categoriesIds").isArray());
    }

    private void insertTestData() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("title");
        book1.setAuthor("author");
        book1.setIsbn("isbn");
        book1.setPrice(BigDecimal.valueOf(100));

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("title1");
        book2.setAuthor("author1");
        book2.setIsbn("isbn1");
        book2.setPrice(BigDecimal.valueOf(100));

        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}

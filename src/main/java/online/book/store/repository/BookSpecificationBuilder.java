package online.book.store.repository;

import lombok.RequiredArgsConstructor;
import online.book.store.dto.BookSearchParametersDto;
import online.book.store.model.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements
        SpecificationBuilder<Book, BookSearchParametersDto> {
    private final SpecificationProviderManager<Book> providerManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> specification = Specification.where(null);

        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            specification = specification.and(providerManager.getSpecificationProvider("author")
                    .getSpecification(searchParameters.authors()));
        }

        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            specification = specification.and(providerManager.getSpecificationProvider("title")
                    .getSpecification(searchParameters.titles()));
        }

        if (searchParameters.prices() != null && searchParameters.prices().length > 0) {
            specification = specification.and(providerManager.getSpecificationProvider("price")
                    .getSpecification(searchParameters.prices()));
        }
        return specification;
    }
}

package online.book.store.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.model.Book;
import online.book.store.repository.BookRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Data
@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Book save(Book book) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t save book to DB", e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can`t get list of books from DB", e);
        }
    }

    @Override
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) throws EntityNotFoundException {
        Book book = entityManagerFactory.createEntityManager().find(Book.class, id);
        if (!book.equals(null)) {
            return book;
        }
        throw new EntityNotFoundException("Entity " + id + " not found");
    }
}

package es.torres.books.service;

import com.github.javafaker.Faker;
import es.torres.books.exception.BookNotFoundException;
import es.torres.books.exception.CommentNotFoundException;
import es.torres.books.model.Book;
import es.torres.books.model.Comment;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private ConcurrentMap<Long, Book> books = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(10);

    public BookService() {
        createFakeData(5);
    }

    public Collection<Book> findAll() {
        return books.values();
    }

    public Book findById(long id) {
        Book book = books.get(id);
        if (book != null) {
            return book;
        } else {
            throw new BookNotFoundException(id);
        }
    }

    public Book save(Book book) {
        long id = nextId.getAndIncrement();
        book.setId(id);
        this.books.put(id, book);
        return book;
    }

    public Comment save(Comment comment, long bookId) {
        Book book = findById(bookId);
        long id = nextId.getAndIncrement();
        comment.setId(id);
        book.getCommentsMap().put(id, comment);
        return comment;
    }

    public void deleteById(long id) {
        this.books.remove(id);
    }

    public void deleteComment(long bookId, long id) {
        findById(bookId).getCommentsMap().remove(id);
        ;
    }

    public Comment findCommentById(long bookId, long id) {
        Comment comment = findById(bookId).getCommentsMap().get(id);
        if (comment != null) {
            return comment;
        } else {
            throw new CommentNotFoundException(id);
        }
    }

    private void createFakeData(int size) {
        Faker faker = new Faker();
        for (int i = 1; i <= size; i++) {
            final Long id = Long.valueOf(i);
            Book book = new Book(id,
                faker.book().title(),
                faker.lorem().paragraph(),
                faker.book().author(),
                faker.book().title(),
                faker.number().numberBetween(1900, 2020), new ConcurrentHashMap<>());
            for (int j = 1; j <= 2; j++) {
                final Long commentId = Long.valueOf(j);
                Comment comment = new Comment(
                    commentId, faker.name().fullName(), faker.lorem().sentence(),
                    Integer.valueOf(faker.number().numberBetween(1, 6)).shortValue());
                book.getCommentsMap().put(commentId, comment);
            }
            books.put(id, book);
        }
    }
}

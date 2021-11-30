package es.torres.books.controller;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import com.fasterxml.jackson.annotation.JsonView;
import es.torres.books.model.Book;
import es.torres.books.model.Comment;
import es.torres.books.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.net.URI;
import java.util.Collection;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    public interface BookDetail extends Book.Basic, Book.Detail, Comment.Basic {

    }

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get a list of books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns the list of books"),
        @ApiResponse(responseCode = "500", description = "If a runtime error occurs while processing the request", content = @Content)})
    @JsonView(Book.Basic.class)
    @GetMapping("/")
    public ResponseEntity<Collection<Book>> getBooks() {
        return ResponseEntity.ok().body(bookService.findAll());
    }

    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the book"),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
        @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)})
    @JsonView(BookDetail.class)
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @Operation(summary = "Delete a book by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "If the book has been deleted successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
        @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Creates a new book")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "If the book has been created successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
        @ApiResponse(responseCode = "400", description = "If one or more request parameters don't comply with the specification", content = @Content),
        @ApiResponse(responseCode = "500", description = "If a runtime error occurs while processing the request", content = @Content)})
    @PostMapping("/")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        book = bookService.save(book);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();
        return ResponseEntity.created(location).body(book);
    }

    @Operation(summary = "Creates a new comment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "If the comment has been created successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))}),
        @ApiResponse(responseCode = "400", description = "If one or more request parameters don't comply with the specification", content = @Content),
        @ApiResponse(responseCode = "500", description = "If a runtime error occurs while processing the request", content = @Content)})
    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable long id, @Valid @RequestBody Comment comment) {
        comment = bookService.save(comment, id);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(comment.getId()).toUri();
        return ResponseEntity.created(location).body(comment);
    }

    @Operation(summary = "Get a comment by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the comment"),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
        @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)})
    @GetMapping("/{bookId}/comments/{id}")
    public ResponseEntity<Comment> getCommentByBookAndId(@PathVariable long bookId, @PathVariable long id) {
        Comment comment = bookService.findCommentById(bookId, id);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a comment by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "If the comment has been deleted successfully", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
        @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)})
    @DeleteMapping("/{bookId}/comments/{id}")
    public ResponseEntity deleteComment(@PathVariable long bookId, @PathVariable long id) {
        bookService.deleteComment(bookId, id);
        return ResponseEntity.noContent().build();
    }

}

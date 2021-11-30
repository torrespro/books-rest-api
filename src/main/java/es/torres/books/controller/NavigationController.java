package es.torres.books.controller;

import es.torres.books.model.Book;
import es.torres.books.model.Comment;
import es.torres.books.service.BookService;
import es.torres.books.service.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NavigationController {

    private final BookService bookService;

    public NavigationController(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    private UserSession userSession;

    @GetMapping("/")
    public String getAllBooksView(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "index";
    }

    @GetMapping("/create")
    public String createBookView(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("comment", new Comment());
        return "new_book";
    }

    @GetMapping("/view/{id}")
    public String showBookView(Model model, @PathVariable long id) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);

        Comment comment = new Comment();
        comment.setUsername(userSession.getUser());
        model.addAttribute("comment", comment);
        return "view_book";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/book/{bookId}/create_comment")
    public String createComment(@ModelAttribute("comment") Comment comment, @PathVariable long bookId) {
        bookService.save(comment, bookId);
        userSession.setUser(comment.getUsername());
        return "redirect:/view/" + bookId;
    }

    @GetMapping("/book/{bookId}/delete_comment/{id}")
    public String deleteComment(@PathVariable long bookId, @PathVariable long id) {
        bookService.deleteComment(bookId, id);
        return "redirect:/view/" + bookId;
    }
}

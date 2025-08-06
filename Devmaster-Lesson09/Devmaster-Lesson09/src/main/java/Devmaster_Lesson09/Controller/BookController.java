package Devmaster_Lesson09.Controller;

import Devmaster_Lesson09.Service.BookService;
import Devmaster_Lesson09.entity.Book;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.PreferencesEvent;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @GetMapping
    public String listBooks(Model model)
    {
        model.addAttribute("books",bookService.getAllBook());
        return "books/book-list";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model)
    {
        model.addAttribute("book",new Book());
        return "books/book-form";
    }
    @PostMapping("/create")
    public String saveBook(@ModelAttribute("book")Book book)
    {
        bookService.saveBook(book);
        return "redirect:/book";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id")Long id,Model model)
    {
        model.addAttribute("book",bookService.getBookById(id).orElse(null));
        return "books/book-form";
    }
    @PostMapping("/create/{id}")
    public String updateBook(@PathVariable Long id,@ModelAttribute Book book)
    {
        book.setId(id);
        bookService.saveBook(book);
        return "redirect:/book";
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id")Long id)
    {
        bookService.deleteBook(id);
        return "redirect:/book";
    }
}

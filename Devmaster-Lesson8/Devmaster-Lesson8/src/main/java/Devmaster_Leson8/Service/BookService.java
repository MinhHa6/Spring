package Devmaster_Leson8.Service;

import Devmaster_Leson8.Repository.BookRepository;
import Devmaster_Leson8.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public BookService(BookRepository bookRepository)
    {
        this.bookRepository=bookRepository;
    }
    public List<Book> getAllBooks ()
    {
        return bookRepository.findAll();
    }
    public Book saveBook(Book book)
    {
        return bookRepository.save(book);
    }
    public Book getBookById(Long id)
    {
        return bookRepository.findById(id).orElse(null);
    }
    public void deleteBook(Long id)
    {
        bookRepository.deleteById(id);
    }
}

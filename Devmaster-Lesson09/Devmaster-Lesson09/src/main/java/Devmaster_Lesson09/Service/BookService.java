package Devmaster_Lesson09.Service;

import Devmaster_Lesson09.Repository.BookRepository;
import Devmaster_Lesson09.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository)
    {
        this.bookRepository=bookRepository;
    }
    // lay ds
    public List<Book> getAllBook()
    {
        System.out.println(bookRepository.findAll());
        return bookRepository.findAll();
    }
    //lay book theo id
    public Optional<Book> getBookById(Long id)
    {
        return bookRepository.findById(id);
    }
    //cap nhat du lieu bang
    public Book saveBook(Book book)
    {
        return bookRepository.save(book);
    }
    //xoa book theo id
    public void deleteBook(Long id)
    {
        bookRepository.deleteById(id);
    }
}

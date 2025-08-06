package Devmaster_Lesson09.Service;

import Devmaster_Lesson09.Repository.AuthorRepository;
import Devmaster_Lesson09.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorsService {
    @Autowired
    private AuthorRepository authorRepository;
    public AuthorsService (AuthorRepository authorRepository)
    {
        this.authorRepository=authorRepository;
    }
    //lay toan bo author
    public List<Author> getAllAuthor(AuthorRepository authorRepository)
    {
        System.out.println(authorRepository.findAll());
        return authorRepository.findAll();
    }
    //doc du lieu bang theo id
    public Optional<Author> findById(Long id)
    {
        return authorRepository.findById(id);
    }
    //cap nhat update
    public Author saveAuthor(Author author)
    {
        return authorRepository.save(author);
    }
    //xoa
    public void deleteAuthor(Long id)
    {
        authorRepository.deleteById(id);
    }
}

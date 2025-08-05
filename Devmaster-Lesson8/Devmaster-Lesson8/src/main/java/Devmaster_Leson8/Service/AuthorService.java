package Devmaster_Leson8.Service;

import Devmaster_Leson8.Repository.AuthorRepository;
import Devmaster_Leson8.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    public List<Author> getAllAuthor()
    {
        return authorRepository.findAll();
    }
    public Author saveAuthor(Author author)
    {
        return authorRepository.save(author);
    }
    public Author getAuthorById(Long id)
    {
        return authorRepository.findById(id).orElse(null);
    }
    public void deleteAuthor(Long id)
    {
        authorRepository.deleteById(id);
    }
    public List<Author> findAllById (List<Long> ids)
    {
        return authorRepository.findAllById(ids);
    }
}

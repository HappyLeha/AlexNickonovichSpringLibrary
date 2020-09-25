package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Cover;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    public void addNewAuthor(Author author)  {
        authorRepository.save(author);
        log.info("Author "+author.toString()+" was created.");
    }
    public Author getById(Integer id) throws ResourceNotFoundException {
        return authorRepository.findAuthorById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
    }
    public void deleteAuthor(Integer authorId) throws ResourceRestrictException {
        if (authorRepository.existsById(authorId)) {
            Author author=authorRepository.findAuthorById(authorId).get();
            if (author.getBooks().size()==0) authorRepository.deleteById(authorId);
            else throw new ResourceRestrictException();
        }
        else return;
        log.info("Author "+authorRepository.
                findAuthorById(authorId).toString()+" was deleted.");
    }
    public void editAuthor(Author author) throws ResourceNotFoundException {
        Author oldAuthor,logAuthor;
        if (authorRepository.existsById(author.getId())) oldAuthor=authorRepository.findAuthorById(author.getId()).get();
        else throw new ResourceNotFoundException();
        logAuthor=new Author(oldAuthor.getId(),oldAuthor.getFirstName(),oldAuthor.getLastName(),
                oldAuthor.getYear(),oldAuthor.getGender(),oldAuthor.getNote(),oldAuthor.getBooks());
        oldAuthor.setFirstName(author.getFirstName());
        oldAuthor.setLastName(author.getLastName());
        oldAuthor.setYear(author.getYear());
        oldAuthor.setGender(author.getGender());
        oldAuthor.setNote(author.getNote());
        authorRepository.save(oldAuthor);
        log.info("Author"+logAuthor.toString()+" was changed to "+oldAuthor.toString()+".");
    }
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }
}

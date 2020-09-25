package com.example.demo.service;

import com.example.demo.dto.SearchDto;
import com.example.demo.entity.Book;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@Transactional
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public void addNewBook(Book book) {
        bookRepository.save(book);
        log.info("Book "+book.toString()+" was created.");
    }
    public Book getById(Integer id) throws ResourceNotFoundException {
        return bookRepository.findBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
    }
    public void deleteBook(Integer bookId) throws ResourceRestrictException{
        if (bookRepository.existsById(bookId)) {
            Book book=bookRepository.findBookById(bookId).get();
            if (book.getCovers().size()!=0) throw new ResourceRestrictException();
            if (book.getRents().size()==0) bookRepository.deleteById(bookId);
            else {
                book.setCount(book.getRents().size());
                bookRepository.save(book);
            }
        }
        else return;
        log.info("Book "+bookRepository.
                findBookById(bookId).toString()+" was deleted.");

    }
    public void editBook(Book book) throws ResourceNotFoundException,ResourceRestrictException {
        Book oldBook,logBook;
        if (bookRepository.existsById(book.getId())) oldBook=bookRepository.findBookById(book.getId()).get();
        else throw new ResourceNotFoundException();
        if (book.getCount()<oldBook.getRents().size()) throw new ResourceRestrictException();
        logBook=new Book(oldBook.getId(),oldBook.getTitle(),oldBook.getYear(),oldBook.getDate(),
                oldBook.getCount(),oldBook.getAuthors(),
                oldBook.getRents(),oldBook.getPublishing(),oldBook.getCovers());
        oldBook.setTitle(book.getTitle());
        oldBook.setYear(book.getYear());
        oldBook.setPublishing(book.getPublishing());
        oldBook.setDate(book.getDate());
        oldBook.setCount(book.getCount());
        oldBook.getAuthors().clear();
        oldBook.getAuthors().addAll(book.getAuthors());
        bookRepository.save(oldBook);
        log.info("Book"+logBook.toString()+" was changed to "+oldBook.toString()+".");
    }
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }
    public List<Book> search(SearchDto searchDto) {
        List<Book> results=bookRepository.findAll().stream().filter(
                i->{if (searchDto.getBookTitle()==null) return true;
                else if (i.getTitle().toLowerCase().contains(searchDto.getBookTitle().toLowerCase())) return true;
                else return false;}).collect(Collectors.toList());
        results=results.stream().filter(i->{if (searchDto.getPublishingTitle()==null) return true;
                else if (i.getPublishing().getTitle().toLowerCase().contains(searchDto.getPublishingTitle().toLowerCase())) return true;
                else return false;}).collect(Collectors.toList());
        results=results.stream().filter(
                i->{if (searchDto.getLastName()==null) return true;
                else {
                    long count=i.getRents().stream().filter(j->{if (j.getUser().getLastName().toLowerCase().contains(searchDto.getLastName().toLowerCase())) return true;
                    else return false;}).count();
                    if (count!=0) return true;
                    else return false;
                 }
                }).collect(Collectors.toList());

        results=results.stream().filter(i->searchDto.getStartYear()==null||i.getYear()>=searchDto.getStartYear()).filter(i->searchDto.getEndYear()==null||i.getYear()<=searchDto.getEndYear()).collect(Collectors.toList());
        return results;

    }
}

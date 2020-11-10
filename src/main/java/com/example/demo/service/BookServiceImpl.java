package com.example.demo.service;

import com.example.demo.dto.BookDeleteDto;
import com.example.demo.entity.Book;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public Book getById(Long id) throws ResourceNotFoundException {
        return bookRepository.findBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    public void deleteBook(Long bookId) throws ResourceRestrictException {
        if (bookRepository.existsById(bookId)) {
            Book book=bookRepository.findBookById(bookId).get();
            if (book.getRents().size()==0) bookRepository.deleteById(bookId);
            else throw new ResourceRestrictException();
        }
        else return;
        log.info("Book "+bookRepository.findBookById(bookId).toString()+" was deleted.");
    }

    public void editBook(Book book) throws ResourceNotFoundException,ResourceRestrictException {
        Book oldBook,logBook;
        if (bookRepository.existsById(book.getId())) oldBook=bookRepository.findBookById(book.getId()).get();
        else throw new ResourceNotFoundException();
        if (book.getCount()<oldBook.getRents().size()) throw new ResourceRestrictException();
        logBook=new Book(oldBook.getId(),oldBook.getTitle(),oldBook.getDate(),oldBook.getCount(),
                oldBook.getAuthors(),oldBook.getPublishing(),oldBook.getRents());
        oldBook.setTitle(book.getTitle());
        oldBook.setPublishing(book.getPublishing());
        oldBook.setDate(book.getDate());
        oldBook.setCount(book.getCount());
        oldBook.setPublishing(book.getPublishing());
        oldBook.setAuthors(book.getAuthors());
        bookRepository.save(oldBook);
        log.info("Book"+logBook.toString()+" was changed to "+oldBook.toString()+".");
    }

    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    public List<Book> getAllFreeBook() {
        return bookRepository.findAll().stream().filter(i->i.getCount()!=i.getRents().size()).
                collect(Collectors.toList());
    }

    public void deleteBooks(ArrayList<BookDeleteDto> books) {
        for (BookDeleteDto bookDto:books) {
            Book book;
            if (bookRepository.existsById(bookDto.getId())) {
                book=bookRepository.findBookById(bookDto.getId()).get();
            }
            else continue;
            if (book.getCount()!=book.getRents().size()) {
              if (book.getCount()!=1) {
                  book.setCount(book.getCount()-1);
                  bookRepository.save(book);
              }
              else {
                  bookRepository.delete(book);
              }
            }
        }
    }

}

package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {

    List<Book> findAll();
    Optional<Book> findBookById(Long bookId);

}

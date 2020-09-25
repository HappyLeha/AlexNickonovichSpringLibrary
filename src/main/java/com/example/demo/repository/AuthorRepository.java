package com.example.demo.repository;

import com.example.demo.entity.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author,Integer>  {
    List<Author> findAll();
    Optional<Author> findAuthorById(Integer authorId);
}

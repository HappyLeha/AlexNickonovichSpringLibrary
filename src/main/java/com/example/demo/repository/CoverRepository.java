package com.example.demo.repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Cover;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoverRepository extends CrudRepository<Cover,Integer> {
    List<Cover> findAll();
    Optional<Cover> findCoverById(Integer coverId);
}

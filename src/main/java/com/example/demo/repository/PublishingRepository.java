package com.example.demo.repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Publishing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublishingRepository extends CrudRepository<Publishing,Integer> {
    List<Publishing> findAll();
    Optional<Publishing> findPublishingById(Integer publishingId);
}

package com.example.demo.repository;

import com.example.demo.entity.Rent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends CrudRepository<Rent,Long> {

    List<Rent> findAll();
    Optional<Rent> findRentById(Long rentId);

}

package com.example.demo.service;

import com.example.demo.entity.Rent;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.repository.RentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;

    @Autowired
    public RentServiceImpl(RentRepository rentRepository) {
        this.rentRepository=rentRepository;
    }

    public Rent getById(Long rentId) throws ResourceNotFoundException {
        return rentRepository.findRentById(rentId)
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    public void addNewRent(Rent rent) throws ResourceRestrictException {
        if (rent.getBook().getCount()==rent.getBook().getRents().size()||
        rent.getUser().getRent()!=null) throw new ResourceRestrictException();
        rentRepository.save(rent);
        log.info("User "+rent.toString()+" was created.");
    }

    public void deleteRent(Long rentId) {
        if (rentRepository.existsById(rentId)) rentRepository.deleteById(rentId);
        else return;
        log.info("Rent "+rentRepository.findRentById(rentId).toString()+" was deleted.");
    }

}

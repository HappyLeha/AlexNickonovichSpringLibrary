package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Cover;
import com.example.demo.entity.Rent;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.repository.CoverRepository;
import com.example.demo.repository.RentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Slf4j
@Service
@Transactional
public class CoverServiceImpl implements CoverService{
    private final CoverRepository coverRepository;
    @Autowired
    public CoverServiceImpl(CoverRepository coverRepository) {
        this.coverRepository=coverRepository;
    }
    public Cover getById(Integer coverId) throws ResourceNotFoundException {
        return coverRepository.findCoverById(coverId)
                .orElseThrow(() -> new ResourceNotFoundException());
    }
    public void addNewCover(Cover cover) {
        coverRepository.save(cover);
        log.info("Cover "+cover.toString()+" was created.");
    }
    public void deleteCover(Integer coverId) {
        if (coverRepository.existsById(coverId)) coverRepository.deleteById(coverId);
        else return;
        log.info("Cover "+coverRepository.
                findCoverById(coverId).toString()+" was deleted.");
    }
    public void editCover(Cover cover) throws ResourceNotFoundException {
        Cover oldCover,logCover;
        if (coverRepository.existsById(cover.getId())) oldCover=coverRepository.findCoverById(cover.getId()).get();
        else throw new ResourceNotFoundException();
        logCover=new Cover(oldCover.getId(),oldCover.getPhoto(),oldCover.getDate(),oldCover.getNote(),oldCover.getBook());
        oldCover.setPhoto(cover.getPhoto());
        oldCover.setNote(cover.getNote());
        oldCover.setBook(cover.getBook());
        coverRepository.save(oldCover);
        log.info("Cover"+logCover.toString()+" was changed to "+oldCover.toString()+".");
    }
    public List<Cover> getAllCover() {
        return coverRepository.findAll();
    }
}

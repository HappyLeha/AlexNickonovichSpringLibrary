package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Publishing;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.PublishingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Slf4j
@Service
@Transactional
public class PublishingServiceImpl implements PublishingService{
    private final PublishingRepository publishingRepository;
    @Autowired
    public PublishingServiceImpl(PublishingRepository publishingRepository) {
        this.publishingRepository = publishingRepository;
    }
    public void addNewPublishing(Publishing publishing)  {
        publishingRepository.save(publishing);
        log.info("Publishing "+publishing.toString()+" was created.");
    }
    public Publishing getById(Integer id) throws ResourceNotFoundException {
        return publishingRepository.findPublishingById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
    }
    public void deletePublishing(Integer publishingId) throws ResourceRestrictException {
        if (publishingRepository.existsById(publishingId)) {
            Publishing publishing=publishingRepository.findPublishingById(publishingId).get();
            if (publishing.getBooks().size()==0) publishingRepository.deleteById(publishingId);
            else throw new ResourceRestrictException();
            log.info("Publishing "+publishingRepository.
                    findPublishingById(publishingId).toString()+" was deleted.");
        }
    }
    public void editPublishing(Publishing publishing) throws ResourceNotFoundException {
        Publishing oldPublishing,logPublishing;
        if (publishingRepository.existsById(publishing.getId())) oldPublishing=publishingRepository.findPublishingById(publishing.getId()).get();
        else throw new ResourceNotFoundException();
        logPublishing=new Publishing(oldPublishing.getId(),oldPublishing.getTitle(),oldPublishing.getCountry(),
                oldPublishing.getAddress(),oldPublishing.getPostIndex(),oldPublishing.getEmail(),
                oldPublishing.getBooks());
        oldPublishing.setTitle(publishing.getTitle());
        oldPublishing.setCountry(publishing.getCountry());
        oldPublishing.setAddress(publishing.getAddress());
        oldPublishing.setPostIndex(publishing.getPostIndex());
        oldPublishing.setEmail(publishing.getEmail());
        publishingRepository.save(oldPublishing);
        log.info("Publishing "+logPublishing.toString()+" was changed to "+oldPublishing.toString()+".");
    }
    public List<Publishing> getAllPublishing() {
        return publishingRepository.findAll();
    }
}

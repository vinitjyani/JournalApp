package com.VinitJyani.journalApp.service;

import com.VinitJyani.journalApp.Entity.JournalEntry;
import com.VinitJyani.journalApp.Entity.User;
import com.VinitJyani.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class journalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
     private UserService userService;



    @Transactional
    public void savaEntry(JournalEntry journalEntry, String userName){
        User user = userService.findbyusername(userName);
        journalEntry.setDate(LocalDateTime.now());
       JournalEntry saved =  journalEntryRepo.save(journalEntry);
       user.getJournalEntriesofuser().add(saved);
       userService.saveuser(user);
    }

    public void savaEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);

    }



    public List<JournalEntry> getall(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findbyid(ObjectId id){
        return journalEntryRepo.findById(id);
    }

    public void deletebyid(ObjectId id, String username){
        try {
            User user = userService.findbyusername(username);
            boolean remove = user.getJournalEntriesofuser().removeIf(x -> x.getId().equals(id));
            if (!remove) {
                throw new RuntimeException("journal entry not found");
            }
            userService.saveuser(user);
            journalEntryRepo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("An error occured while deleting entry",e);
        }
    }


}

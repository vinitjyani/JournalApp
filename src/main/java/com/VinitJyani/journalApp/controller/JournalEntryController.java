package com.VinitJyani.journalApp.controller;

import com.VinitJyani.journalApp.Entity.JournalEntry;
import com.VinitJyani.journalApp.Entity.User;
import com.VinitJyani.journalApp.service.UserService;
import com.VinitJyani.journalApp.service.journalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {


    @Autowired
    private journalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.savaEntry(myEntry,userName);
            return new ResponseEntity<JournalEntry>(myEntry,HttpStatus.CREATED);
        } catch (Exception e) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public ResponseEntity<?> getalljournalentriesofUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findbyusername(userName);
        List<JournalEntry> all = user.getJournalEntriesofuser();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> findbyId(@PathVariable ObjectId id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findbyusername(userName);
       List<JournalEntry> collect =  user.getJournalEntriesofuser().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
       if(!collect.isEmpty()){
           Optional<JournalEntry> jojo = journalEntryService.findbyid(id);
           if(jojo.isPresent()){
               return new ResponseEntity<JournalEntry>(jojo.get(), HttpStatus.OK);
           }
       }
        return  new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }
//
//
//
//
    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deletebyid(@PathVariable ObjectId id){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.deletebyid(id,username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("An error occured while deleting entry",e);
        }
    }
//
//
//
    @PutMapping("id/{id}")
    public boolean updateentry(@PathVariable ObjectId id ,
                               @RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findbyusername(username);
        List<JournalEntry> collect =  user.getJournalEntriesofuser().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            Optional<JournalEntry> jojo = journalEntryService.findbyid(id);
            if (jojo.isPresent()) {
                JournalEntry old  = jojo.get();
                old.setTitle(newEntry.getTitle() != null && newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && newEntry.equals("") ? newEntry.getContent() : old.getContent());
            }
        }
        newEntry.setDate(LocalDateTime.now());
        journalEntryService.savaEntry(newEntry);
        return true;
    }
}

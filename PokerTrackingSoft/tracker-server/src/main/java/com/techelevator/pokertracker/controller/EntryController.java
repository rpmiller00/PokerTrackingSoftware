package com.techelevator.pokertracker.controller;

import com.techelevator.pokertracker.dao.AccountDao;
import com.techelevator.pokertracker.dao.EntryDao;
import com.techelevator.pokertracker.dao.TransferDao;
import com.techelevator.pokertracker.dao.UserDao;
import com.techelevator.pokertracker.exception.DaoException;
import com.techelevator.pokertracker.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class EntryController {
    private UserDao userDao;
    private EntryDao entryDao;

    public EntryController(EntryDao entryDao) {

        this.entryDao = entryDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/addEntry", method = RequestMethod.POST)
    public Entry createEntry(@RequestBody Entry newEntry) {
        Entry createdEntry = null;
        try {
           createdEntry = entryDao.addEntry(newEntry);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create new entry!");
        }
        return createdEntry;
    }

    @RequestMapping(path = "/entryList/{id}", method = RequestMethod.GET)
    public List<Entry> getEntries(@PathVariable int id) {
        List<Entry> entries = new ArrayList<>();
        try {
            entries = entryDao.getEntriesByUserId(id);

        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (entries.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Transfer Found");
        }
        return entries;

    }

}

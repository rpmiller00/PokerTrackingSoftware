package com.techelevator.pokertracker.dao;

import com.techelevator.pokertracker.model.Entry;

import java.util.List;

public interface EntryDao {

    Entry getEntryById(int entryId);
    Entry addEntry(Entry newEntry);

    List<Entry> getEntriesByUserId(int userId);
}

package com.techelevator.pokertracker.dao;

import com.techelevator.pokertracker.exception.DaoException;
import com.techelevator.pokertracker.model.Entry;
import com.techelevator.pokertracker.model.Transfer;
import com.techelevator.pokertracker.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcEntryDao implements EntryDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcEntryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Entry> getEntriesByUserId(int userId){
        List<Entry> entries = new ArrayList<>();
        String sql = "SELECT user_id, entry_id, user_id, amount, game_size, game_type, location FROM entry WHERE user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Entry entry = mapRowToEntry(results);
                entries.add(entry);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return entries;
    }

    public Entry getEntryById(int entryId){
        Entry entry = null;
        String sql = "SELECT entry_id, user_id, amount, game_size, game_type, location FROM entry WHERE entry_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, entryId);
            if (results.next()) {
                entry = mapRowToEntry(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return entry;
    }
    public Entry addEntry(Entry newEntry){
        Entry createdEntry = null;

        String sql = "INSERT INTO entry (user_id, amount, game_size, game_type, location) VALUES (?, ?, ?, ?, ?) RETURNING entry_id";
        try {
            int newEntryId = jdbcTemplate.queryForObject(sql, int.class, newEntry.getUserId(), newEntry.getAmount(), newEntry.getGameSize(),
                    newEntry.getGameType(), newEntry.getLocation());
            createdEntry = getEntryById(newEntryId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return createdEntry;
    }

    private Entry mapRowToEntry(SqlRowSet rs) {
        Entry entry = new Entry();
        entry.setEntryId(rs.getInt("entry_id"));
        entry.setUserId(rs.getInt("user_id"));
        entry.setAmount(rs.getBigDecimal("amount"));
        entry.setGameSize(rs.getString("game_size"));
        entry.setGameType(rs.getString("game_type"));
        entry.setLocation(rs.getString("location"));

        return entry;
    }
}

package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Entry {
    private int entryId;
    private int userId;
    private BigDecimal amount;
    private String gameSize;
    private String gameType;
    private String location;

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getGameSize() {
        return gameSize;
    }

    public void setGameSize(String gameSize) {
        this.gameSize = gameSize;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Entry(int userId, BigDecimal amount, String gameSize, String gameType, String location) {
        this.userId = userId;
        this.amount = amount;
        this.gameSize = gameSize;
        this.gameType = gameType;
        this.location = location;
    }

    public Entry(){

    }

    @Override
    public String toString() {
        return  "Amount: $" + amount + "\n"+
                "Game Size: " + gameSize + "\n"+
                "Game Type: " + gameType + "\n"+
                "Location" + location;
    }
}

package com.morpembot.MorphemBot.dataBase;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usersDataTable")
public class User {
    @Id
    private long userId;
    private boolean parseCheck;

    public long getUserId(){
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isParseCheck(){
        return parseCheck;
    }

    public void setParseCheck(boolean parseCheck) {
        this.parseCheck = parseCheck;
    }
}

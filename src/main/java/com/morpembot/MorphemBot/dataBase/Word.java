package com.morpembot.MorphemBot.dataBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "wordsDataTable")
public class Word {
    @Id
    private long wordId;
    @Column(columnDefinition = "TEXT")
    private String entered;
    @Column(columnDefinition = "TEXT")
    private String prefix;
    @Column(columnDefinition = "TEXT")
    private String root;
    @Column(columnDefinition = "TEXT")
    private String suffix;
    @Column(columnDefinition = "TEXT")
    private String ending;
    @Column(columnDefinition = "TEXT")
    private String base;

    public long getWordId(){
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public String getEntered(){
        return entered;
    }

    public void setEntered(String entered) {
        this.entered = entered;
    }

    public String getPrefix(){
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getRoot(){
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getSuffix(){
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getEnding(){
        return ending;
    }

    public void setEnding(String ending) {
        this.ending = ending;
    }

    public String getBase(){
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

}

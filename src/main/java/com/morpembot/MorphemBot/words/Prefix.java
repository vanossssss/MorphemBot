package com.morpembot.MorphemBot.words;

public class Prefix implements WordPart{
    private String prefix;

    public Prefix(String prefix) {
        this.prefix = prefix;
    }

    public String getWordPart(){
        return prefix;
    }

    public void setWordPart(String base) {
        this.prefix = base;
    }
}

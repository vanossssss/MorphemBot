package com.morpembot.MorphemBot.words;

public class Suffix implements WordPart{
    private String suffix;

    public Suffix(String suffix) {
        this.suffix = suffix;
    }

    public String getWordPart(){
        return suffix;
    }

    public void setWordPart(String base) {
        this.suffix = base;
    }
}

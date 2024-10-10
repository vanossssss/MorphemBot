package com.morpembot.MorphemBot.words;

public class Base implements WordPart{
    private String base;

    public Base(String base) {
        this.base = base;
    }


    public String getWordPart(){
        return base;
    }

    public void setWordPart(String base) {
        this.base = base;
    }
}

package com.morpembot.MorphemBot.words;

public class Ending implements WordPart{
    private String ending;

    public Ending(String ending) {
        this.ending = ending;
    }

    public String getWordPart(){
        return ending;
    }

    public void setWordPart(String base) {
        this.ending = base;
    }
}

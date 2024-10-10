package com.morpembot.MorphemBot.words;

public class Linking implements WordPart{
    private String linking;

    public Linking(String linking) {
        this.linking = linking;
    }

    public String getWordPart(){
        return linking;
    }

    public void setWordPart(String base) {
        this.linking = base;
    }
}

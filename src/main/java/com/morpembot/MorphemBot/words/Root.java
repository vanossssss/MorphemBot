package com.morpembot.MorphemBot.words;

public class Root implements WordPart{
    private String root;

    public Root(String root) {
        this.root = root;
    }

    public String getWordPart(){
        return root;
    }

    public void setWordPart(String base) {
        this.root = root;
    }
}

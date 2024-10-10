package com.morpembot.MorphemBot.words;

import java.util.ArrayList;

public class Word {

    private ArrayList<WordPart> word;

    public Word(ArrayList<WordPart> word) {
        this.word = word;
    }

    public ArrayList<WordPart> getWord() {
        return word;
    }

    public void setWord(ArrayList<WordPart> word) {
        this.word = word;
    }
}

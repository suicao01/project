package com.example.demo;

import java.util.HashMap;



public class Dictionary1 {
    private HashMap<String,Word> wordList;
    public Dictionary1(){
        wordList = new HashMap<>();
    }
    public void addWord(Word w){
        wordList.put(w.getWord_target(),w);
    }
    public HashMap<String, Word> getWordList(){
        return wordList;
    }
}

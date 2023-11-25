package com.example.demo;

import java.util.TreeMap;



public class Dictionary1 {
    private TreeMap<String,Word> wordList;
    public Dictionary1(){
        wordList = new TreeMap<>();
    }
    public void addWord(Word w){
        wordList.put(w.getWord_target(),w);
    }
    public TreeMap<String, Word> getWordList(){
        return wordList;
    }
}

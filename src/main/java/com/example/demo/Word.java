package com.example.demo;

public class Word {
    private String word_target;
    private String word_explain;
    public Word (String word, String explain){
        this.word_target = word;
        this.word_explain = explain;
    }
    public String getWord_target(){
        return word_target;
    }
    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
}

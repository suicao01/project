package com.example.demo;

public class DictionaryCommandLine {
    private DictionaryManagement dictionaryManagement;
    public DictionaryCommandLine() {
        dictionaryManagement = new DictionaryManagement();
    }
    public void dictionaryBasic (){
        Dictionary1 d = new Dictionary1();
        dictionaryManagement.insertFromCommandline();

    }
    public static void main (String []args) {
        DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();
        dictionaryCommandLine.dictionaryBasic();
    }
}

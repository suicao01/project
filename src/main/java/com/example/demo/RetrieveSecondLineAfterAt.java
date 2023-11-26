package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class RetrieveSecondLineAfterAt {
    public TreeMap<String,Word> insertFile() {
        Dictionary1 dictionary1 = new Dictionary1();

        try (BufferedReader br = new BufferedReader(new FileReader("dtb.txt"))) {
            String line;
            String target = null;
            String explain = null;
            boolean retrieveNextLine = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("@")) {
                    if (target != null && explain != null) {
                        dictionary1.addWord(new Word(target, explain));
                    }

                    target = line.substring(1, line.indexOf(" ")).trim();
                    explain = "";
                } else if (line.contains("*")) {
                    retrieveNextLine = true;
                } else if (retrieveNextLine) {

                        int dashIndex = line.indexOf("-");

                            explain = line.substring(dashIndex + 1).trim();

                        retrieveNextLine = false;

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        dictionary1.getWordList().entrySet().removeIf(entry -> entry.getValue().getWord_explain().isEmpty());
                dictionary1.getWordList().entrySet().removeIf(entry -> entry.getValue().getWord_explain().equals("("));

      return dictionary1.getWordList();
    }


    public String dictionaryLookup(String word) {
        for (String i : insertFile().keySet()){
            if (i.equals(word)){
                Word newWords = insertFile().get(i);
                return newWords.getWord_explain();
            }


        }
        return null;
    }
}

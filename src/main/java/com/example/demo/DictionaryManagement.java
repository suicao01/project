package com.example.demo;

import java.io.*;
import java.util.*;

public class DictionaryManagement extends Dictionary1 {
    public void insertFromCommandline(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Input number of words: ");
        int n= sc.nextInt();
        sc.nextLine();
        for (int i=0;i<n;i++){
            System.out.print("Input a word: ");
            String e = sc.nextLine();
            System.out.print("Input its meaning: ");
            String m = sc.nextLine();
            Word word = new Word(e,m);
           addWord(word);

        }
    }

    public void insertFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("dtb.txt"))) {
            String line;
            String target = null;
            String explain = null;
            while ((line = br.readLine()) != null) {
                if (target != null) {
                    addWord(new Word(target, explain));
                }
                if (line.charAt(0) == '@') {
                    target = line.substring(1, line.indexOf("/") - 1);
                    explain = (line.substring(line.indexOf("/")) + "\n");


                } else {
                    explain += line;
                    explain += "\n";
                }

            }
            br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }

    }

    public void exportToFile(String word, String path) throws IOException {
        try
        {
            FileWriter fw = new FileWriter(path,true); //the true will append the new data
            fw.write(word+"\n");//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
    public String dictionaryLookup(String word) {
        for (String i : getWordList().keySet()){
            if (i.equals(word)){
                Word newWords = getWordList().get(i);
                return newWords.getWord_explain();
            }
        }
        return null;
    }

    // return tu tieng anh dc tra
    public String getWordLookedUp(String word) {
        for (String i : getWordList().keySet()){
            if (i.equals(word)){
                Word newWords = getWordList().get(i);
                return newWords.getWord_target();
            }
        }
        return null;
    }

    public void deleteWord (String word) {
        getWordList().remove(word);
    }

    public void addNewWord(String word, String explain){
        getWordList().put(word,new Word(word,explain));
    }

    public String dictionarySearcher(String prefix){
        StringBuilder res = new StringBuilder();
        for (Map.Entry<String,Word> entry: getWordList().entrySet()){
            String word = entry.getKey();
            if (word.startsWith(prefix)){
                res.append(entry.getValue().getWord_target()).append("\n");
            }
        }
        return res.toString();
    }

    public void changeWord(String word, String explain) {
       if (getWordList().containsKey(word)){
           getWordList().get(word).setWord_explain(explain);
       }

    }

}

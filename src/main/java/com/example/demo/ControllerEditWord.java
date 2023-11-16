package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.*;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Map;

public class ControllerEditWord extends Controller {



    @FXML
    TextField editWordTextField ;

    @FXML
    TextArea editMeaningTextField ;

    @FXML
    Button buttonSave;

    @FXML
    Button buttonBackFind;

    @FXML
    Label thong_bao_da_save;
    public String getHienThiSearchedWord() {
        String searched_word = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader("editWord.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                searched_word = line;
            }
        } catch (IOException e){
            e.printStackTrace();
        }



        return searched_word;


    }
    public String getHienThiNghiaSearchedWord(){
        String searched_word = "";
        String hienThiNghia ="";
        try{
            BufferedReader br = new BufferedReader(new FileReader("editWord.txt"));
            String line;
            while ((line = br.readLine()) != null && !line.equals(" ")) {
                searched_word = line;
            }
            hienThiNghia = getManage().dictionaryLookup(searched_word);
        } catch (IOException e){
            e.printStackTrace();
        }



        return hienThiNghia;


    }

    public void setEditWordTextField(ActionEvent event){

        editWordTextField.setText(getHienThiSearchedWord());
        editMeaningTextField.setText(getHienThiNghiaSearchedWord());
    }

    public void getMeaning(ActionEvent event) throws IOException {

        editMeaningTextField.setText(getManage().dictionaryLookup(editWordTextField.getText()));
    }

    public void replace(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn sửa từ này?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            thong_bao_da_save.setText("You saved successfully!");
            DictionaryManagement dictionaryManagement = new DictionaryManagement();
            dictionaryManagement.insertFile();
            String word = editWordTextField.getText();
            String explain = editMeaningTextField.getText();
            dictionaryManagement.changeWord(word, explain);

            StringBuilder res = new StringBuilder();
            for (Map.Entry<String, Word> entry : dictionaryManagement.getWordList().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().getWord_explain();
                res.append("@").append(key).append(" ").append(value);
            }


            try (BufferedWriter bw = new BufferedWriter(new FileWriter("dtb.txt"))) {
                bw.write(res.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

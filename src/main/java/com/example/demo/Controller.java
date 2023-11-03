package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    TextField search;
    @FXML
    private Button exit;

    @FXML
    private AnchorPane ScenePane;
    Stage stage;
    @FXML
    Label meaning;

    @FXML
    public void Logout(ActionEvent event) {
       stage = (Stage) ScenePane.getScene().getWindow();
       stage.close();
    }


    public void findWord(ActionEvent event) throws IOException {
        DictionaryManagement d = new DictionaryManagement();
        d.insertFile();
        String word = search.getText();
        //d.changeWord("love","Yen Ngoc");
        meaning.setText("Meaning:\n" + d.dictionaryLookup(word));
    }
}
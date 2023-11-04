package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    Scene scene;
    Parent root;

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
    Text textMeaning;
    @FXML
    public void Logout(ActionEvent event) {
       stage = (Stage) ScenePane.getScene().getWindow();
       stage.close();
    }

    // tim ra tu tieng anh
    public void findWord(ActionEvent event) throws IOException {
        DictionaryManagement d = new DictionaryManagement();
        d.insertFile();
        String word = search.getText();
        //d.changeWord("love","Yen Ngoc");
        meaning.setText(d.getWordLookedUp(word));
        textMeaning.setText(d.dictionaryLookup(word));
    }

    public void switchToSceneSearch(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("l.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneAddWord(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("addWord.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;






public class Controller implements Initializable{


    Scene scene;
    Parent root;
    @FXML
    private ListView<String> listWord;

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
    Label function;
    @FXML
    private Button history;
    @FXML
    Text textMeaning;
    private final String path = "history.txt";

    protected DictionaryManagement getManage() throws IOException {
        DictionaryManagement d = new DictionaryManagement();
        d.insertFile();
        return d;
    }

    @FXML
    public void Logout(ActionEvent event) {
        stage = (Stage) ScenePane.getScene().getWindow();
        stage.close();
    }

    // tim ra tu tieng anh
    public void findWord() throws IOException {
        String word = search.getText();
        if (word.isEmpty()) {
            textMeaning.setText("");
            meaning.setText("");
        }

        meaning.setText(getManage().getWordLookedUp(word));
        textMeaning.setText(getManage().dictionaryLookup(word));
        if (!word.isEmpty()) {
            getManage().exportToFile(word, path);
        }
    }


    public void switchToSceneSearch(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("l.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // hien ra man hinh lich su tim kiem
    public void hisScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("his.fxml")));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


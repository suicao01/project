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
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
public class Controller implements Initializable{


    public Button export_to_file1;
    public TextArea textMeaning;
    public Button voice;

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
    Label function;
    @FXML
    private Button history;

    private final String edit_word_path = "editWord.txt";
    private final String path = "history.txt";

    protected DictionaryManagement getManage() throws IOException {
        DictionaryManagement d = new DictionaryManagement();
        d.insertFile();
        return d;
    }

    @FXML
    public void Logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to exit?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) ScenePane.getScene().getWindow();
            stage.close();
        }
    }

    // tim ra tu tieng anh
    public void findWord() throws Exception {
        String word = search.getText();
        // ghi de k giu luu tam old
        FileWriter fw = new FileWriter(edit_word_path);
        // luu tam word vao file editWord
        getManage().exportToFile(word,edit_word_path);
        if (word.isEmpty()) {
            textMeaning.setText("");
            meaning.setText("");
        }
        voice.setVisible(true);
        textMeaning.setVisible(true);
        meaning.setVisible(true);
        meaning.setText(getManage().getWordLookedUp(word));
        textMeaning.setText(getManage().dictionaryLookup(word));
        if (!word.isEmpty()) {
            getManage().exportToFile(word, path);
        }

    }

    private Sound getSound() throws Exception {
        Sound s = new Sound();
        s.exportSound(meaning.getText());
        return s;
    }

    public void switchToSceneSearch(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("l.fxml")));
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
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addWord.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneEditWord(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editWord.fxml")));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);


        stage.setScene(scene);

        stage.show();

    }

    public void Sound(ActionEvent event) throws Exception {

        if (!meaning.getText().isEmpty()) {
         getSound().playSound();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


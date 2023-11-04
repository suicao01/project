package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HistoryController extends Controller implements Initializable {


    public Label showWord;
    public Text definition;


    @FXML
    private ListView<String> listWord;
    private Stage stage;
    private Scene scene;
    private final String path = "history.txt";
    private ArrayList<String> words = new ArrayList<>();
    String selectedWord;

//mang de luu tu tu file history
    public ArrayList<String> addW() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line=br.readLine())!= null){
                words.add(line);
            }
        }

        Set<String> temp = new LinkedHashSet<>(words);
        words.clear();
        words.addAll(temp);
        return words;
    }



// tro lai man hinh chinh
        public void MainScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("l.fxml")));

        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    // hien cac tu da tra
        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            try {
                listWord.getItems().addAll(addW());
               listWord.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                   @Override
                   public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                       selectedWord = listWord.getSelectionModel().getSelectedItem();
                       showWord.setText(selectedWord);
                       try {
                           definition.setText(getManage().dictionaryLookup(selectedWord));
                       } catch (IOException e) {
                           throw new RuntimeException(e);
                       }
                   }
               });

                } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
}


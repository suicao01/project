package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class HistoryController extends Controller implements Initializable {


    public Label showWord;
    public TextArea definition;
    public Button deleteHis;
    public Label noWordSearch;


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


    // hien cac tu da tra
        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            try {
                Collections.reverse(addW());
                if (addW().isEmpty()) {

                   noWordSearch.setText("Không có lịch sử tìm kiếm nào!");

                   listWord.setVisible(false);
                   definition.setVisible(false);
                }
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

    public void deleteHistory(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn xóa lịch sử?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            File file = new File(path);
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
            listWord.setVisible(false);
            showWord.setVisible(false);

            noWordSearch.setText("Không có lịch sử tìm kiếm nào!");

            definition.setVisible(false);
        }

    }
}


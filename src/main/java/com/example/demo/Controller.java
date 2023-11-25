package com.example.demo;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable  {

// -------tu lien quan--------------------
    @FXML
    protected ListView<String> suggested_Word_ListView;

    protected final ObservableList<String> search_list = FXCollections.observableArrayList();

    ArrayList<String> TempListView = new ArrayList<>();

//-------------------------------------------


    public ImageView dora_run;
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

    //============ GAME ===========
//    @FXML
//    private ImageView dora_run;
//
//
//
//    @FXML
//    private AnchorPane pane_game;
//=======================================
    private final String edit_word_path = "editWord.txt";
    private final String path = "history.txt";

    private final String path_delete = "dtb.txt";



    protected DictionaryManagement getManage() throws IOException {
        DictionaryManagement d = new DictionaryManagement();
        d.insertFile();
        return d;
    }


//-----------------tu lien quan--------------------

    public void setSearchListViewItem() throws IOException {
        try {
            search_list.clear();
            if (search.getText().isEmpty()) {
                TempListView.clear();
                TempListView.addAll(get_key_list());
            }
            for (String temp : TempListView) {
                search_list.add(temp);
            }
            suggested_Word_ListView.setItems(search_list);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static int isContain(String s1, String s2) {
        for (int i = 0; i < Math.min(s1.length(), s2.length()); i++) {
            if (s1.charAt(i) > s2.charAt(i)) {
                return 1;
            } else if (s1.charAt(i) < s2.charAt(i)) {
                return -1;
            }
        }
        if (s1.length() > s2.length()) {
            return 1;
        }
        return 0;
    }
    public static int binaryLookup(int start, int end, String word, ArrayList<String> w) {
        if (end < start) {
            return -1;
        }
        int mid = start + (end - start) / 2;
        int compare = isContain(word, w.get(mid));
        if (compare == -1) {
            return binaryLookup(start, mid - 1, word, w);
        } else if (compare == 1) {
            return binaryLookup(mid + 1, end, word, w);
        } else {
            return mid;
        }
    }

    public ArrayList<String> get_key_list() throws IOException {
        ArrayList<String> DS_key = new ArrayList<>();
        try {

            Set<String> key_is_word = getManage().getWordList().keySet();
            for (String key : key_is_word) {
                DS_key.add(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DS_key;
    }

    public void updateWordInListView(String word, int index, ArrayList<String> res, ArrayList<String> des) throws IOException {
        if (index < 0) {
            return;
        }
        int j = index;
        while (j >= 0) {
            if (isContain(word, res.get(j)) == 0) {
                j--;
            } else {
                break;
            }

        }
        for (int i = j + 1; i <= index; i++) {
            des.add(res.get(i));
        }
        for (int i = index + 1; i < res.size(); i++) {
            if (isContain(word, res.get(i)) == 0) {
                des.add(res.get(i));
            } else {
                break;
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

//    public void checkSeachField() throws IOException {
//        String word = search.getText();
//        int index = binaryLookup(0, get_key_list().size() - 1, word, get_key_list());
//        updateWordInListView(word, index, get_key_list(),keyListView);
//        setSearchListViewItem();
//    }

    public void ClickListView() throws Exception {
        String wordInListView = suggested_Word_ListView.getSelectionModel().getSelectedItem();
        if (wordInListView == null) {
            return;
        }
        search.setText(wordInListView);

        findWord();
    }


//-------------------------------------------------
    @FXML
    public void Logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có muốn thoát chương trình?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) ScenePane.getScene().getWindow();
            stage.close();
        }
    }

    // tim ra tu tieng anh
    public void findWord() throws Exception {
        TempListView.clear();
        search_list.clear();
        String word = search.getText();


        if (word.isEmpty()) {
            textMeaning.setText(null);
            meaning.setText(null);
            setSearchListViewItem();
        }

        textMeaning.setVisible(true);
        meaning.setVisible(true);
        meaning.setText(getManage().getWordLookedUp(word));
        textMeaning.setText(getManage().dictionaryLookup(word));
        if (meaning.getText() != null) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(edit_word_path));
            bw.write(meaning.getText());
            voice.setVisible(true);
        }

        int index = binaryLookup(0, get_key_list().size() - 1, word, get_key_list());
        updateWordInListView(word, index, get_key_list(),TempListView);
        setSearchListViewItem();

        if (!word.isEmpty()) {
            getManage().exportToFile(word, path);
        }

    }

    private Sound getSound() throws Exception {
        Sound s = new Sound();
        s.exportSound(meaning.getText(),"en-gb");
        return s;
    }

    public void switchToSceneSearch(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("l.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGuideScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("guide.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
//=========================   GAME   =======================================
    public void switch_To_Scene_Game(ActionEvent event) throws IOException {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);


            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {e.printStackTrace();e.getMessage();}
        //------------dora animation --------------


    }


    // hien ra man hinh lich su tim kiem
    public void hisScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("his.fxml")));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void switchToMenuScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menuGame.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
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

    public void switch_API(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GoogleTranslate.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void Sound(ActionEvent event) throws Exception {

        if (!meaning.getText().isEmpty()) {
         getSound().playSound();
        }
    }






    public void delete(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn xóa từ này?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            DictionaryManagement dictionaryManagement = new DictionaryManagement();
            dictionaryManagement.insertFile();
            String word = meaning.getText();
            dictionaryManagement.deleteWord(word);
          meaning.setText(null);
          textMeaning.setText(null);
          voice.setVisible(false);
            StringBuilder res = new StringBuilder();
            for (Map.Entry<String, Word> entry : dictionaryManagement.getWordList().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().getWord_explain();
                res.append("@").append(key).append(" ").append(value);
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path_delete))) {
                bw.write(res.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


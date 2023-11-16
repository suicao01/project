package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;

public class ControllerAddWord extends Controller {
    public TextArea meaningWordTextField;
    private Stage stage;

    @FXML
    TextField addWordTextField;
    @FXML
    TextField phienAmTextField;
    @FXML
    TextField loaiTuTextField;
  
    @FXML
    Button buttonSaveNewWord;
    @FXML
    Label notificationAddWordSuccess;

    public void saveNewWord(ActionEvent event) {

        String newWord = ""; newWord = addWordTextField.getText();
        String phienAm = ""; phienAm = phienAmTextField.getText();
        String loaiTu = ""; loaiTu = loaiTuTextField.getText();
        String meaningOfNewWord = ""; meaningOfNewWord = meaningWordTextField.getText();

        String res = "";
        res += "@" + newWord + " /" + phienAm +"/\n"
        + "*  " + loaiTu + "\n- " + meaningOfNewWord +'\n' +"n" +'\n';

        // ghi vao file dtb.txt
        try {
            FileWriter fw = new FileWriter("dtb.txt", true);
            fw.write(res);
            fw.close();
            notificationAddWordSuccess.setText("You add the word success!");
        } catch (Exception e) {
            System.out.println(e);
        }



    }
}
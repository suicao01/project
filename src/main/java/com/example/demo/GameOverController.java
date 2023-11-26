package com.example.demo;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameOverController extends GameController implements Initializable {

    public Label result;

    private String readFile() {
        String res = null;
        try (BufferedReader br = new BufferedReader(new FileReader("score.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                res = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        result.setText(readFile() + "/10");
    }
}

package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTranslatorAPI extends Controller implements Initializable {
    @FXML
    public TextField langto1;

    @FXML
    public TextField langto2;

    @FXML
    public TextField langto3;




    @FXML
    public Tab tab2;

    @FXML
    public Tab tab3;

    @FXML
    public Tab tab4;


    @FXML
    public Button voice;

    @FXML
    public TextField wordFind;
    public TabPane langTo;

    public void DetectLang(ActionEvent event) throws Exception {
        Sound s = new Sound();
        if (!wordFind.getText().isEmpty()) {
            s.exportSound(wordFind.getText(),"en-gb");
        }

     if( langTo.getSelectionModel().isSelected(0)){
         setLangto1();
     }
     else if (langTo.getSelectionModel().isSelected(1)) {
         setLangto2();
     }
     else if (langTo.getSelectionModel().isSelected(2)) {
         setLangto3();
     }
    }


public void setLangto1() throws IOException {
        langTo.getSelectionModel().select(tab2);
        Translator translator = new Translator();
        if (!wordFind.getText().isEmpty()) {
            langto1.setText(translator.translate("", "vi", wordFind.getText()));
        }
        else langto1.setText(null);
}

public void setLangto2() throws IOException {
    langTo.getSelectionModel().select(tab3);
    Translator translator = new Translator();
    if (!wordFind.getText().isEmpty()) {
        langto2.setText(translator.translate("", "en", wordFind.getText()));
    }
    else langto2.setText(null);
}

    public void setLangto3() throws IOException {
        langTo.getSelectionModel().select(tab4);
        Translator translator = new Translator();
        if (!wordFind.getText().isEmpty()) {
            langto3.setText(translator.translate("", "ko", wordFind.getText()));
        }
        else langto3.setText(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        langTo.getSelectionModel().selectedItemProperty().addListener((observableValue, oldTab, newTab) -> {
            if (newTab == tab2) {
                try {
                    setLangto1();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (newTab == tab3) {
                try {
                    setLangto2();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (newTab == tab4) {
                try {
                    setLangto3();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}


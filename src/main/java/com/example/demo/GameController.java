package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Set;

public class GameController extends Controller implements Initializable {

    public Label question;
    public Button choice1;
    public Button choice4;
    public Button choice2;
    public Button choice3;
    public Label number;

    //============ GAME ===========
    @FXML
    private ImageView dora_run;

    @FXML
    private AnchorPane pane_game;
    //=================================
    static String word;
    static String mean;
    ArrayList<String> choice = new ArrayList<>();
    ArrayList<String> temp = new ArrayList<>();

    public Button[] buttons;
    static int score = 0;
    private int switchCount =1;

    //lay random tu trong mang
    private String getRandomWord() throws IOException {
        Set<String> keys = getManage().getWordList().keySet();
        ArrayList<String> list = new ArrayList<>(keys);
        Collections.shuffle(list);
        return list.get(0);
    }

    //hien diem so
    public void ScoreResult() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Quiz Score");
            alert.setHeaderText(null);
            alert.setContentText("Your score: " + score +"/10");
            alert.showAndWait();
        });

    }
    //hien cau hoi
    public void setQuestion() throws IOException {
        for (int i = 0; i < 4; i++) {
            temp.add(getRandomWord());
        }
        word = temp.get(0);

        question.setText(word + " có nghĩa nào trong các đáp án sau:");

    }


    //ktra dap an dung
    private boolean isCorrect(Button button) {
        if (button.getText().equals(mean)) {

            return true;
        }
        return false;
    }

    //thay doi mau nut co dap an dung
    private void changeColor() {
        for (int i = 0; i < buttons.length; i++) {
            if (isCorrect(buttons[i])) {

                buttons[i].setStyle("-fx-background-color: red;");
            }
        }
    }


    private void resetColor() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setStyle("");
        }
    }

    //chuyen cau hoi
    public void switchQuestion() {
        for (int i = 0; i < buttons.length; i++) {

            int finalI = i;
            buttons[i].setOnAction(event -> {
                // Change color of correct button first
                changeColor();
           if (isCorrect(buttons[finalI])) {
               score++;
           }
                // Delay the quiz switch for a few seconds
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> {
                    try {
                        // Reset button colors
                        resetColor();
                        choice.clear();
                        temp.clear();
                        switchCount++;
                        // Switch to another quiz
                        if (switchCount<=10) {
                            number.setText("Câu " + switchCount);
                            setQuestion();
                            setAnswer();
                        }
                        else{
                     ScoreResult();
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                pause.play();
            });
        }
    }


    //hien cac lua chon
    public void setAnswer() throws IOException {
        Translator translator = new Translator();
        for (int i = 0; i < temp.size(); i++) {
            choice.add(translator.translate("", "vi", temp.get(i)));

        }
        mean = choice.get(0);

        Collections.shuffle(choice);

        for (int i = 0; i < choice.size(); i++) {
            buttons[i].setText(choice.get(i));
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttons = new Button[]{choice1, choice2, choice3, choice4};
        try {

            setQuestion();
            setAnswer();
            switchQuestion();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Load sprite image
        Image[] sprite_dora = new Image[6];

        sprite_dora[0] = new Image("C:\\Users\\Admin\\IdeaProjects\\project-Nh\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora1.png");
        sprite_dora[1] = new Image("C:\\Users\\Admin\\IdeaProjects\\project-Nh\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora2.png");
        sprite_dora[2] = new Image("C:\\Users\\Admin\\IdeaProjects\\project-Nh\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora3.png");
        sprite_dora[3] = new Image("C:\\Users\\Admin\\IdeaProjects\\project-Nh\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora4.png");
        sprite_dora[4] = new Image("C:\\Users\\Admin\\IdeaProjects\\project-Nh\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora5.png");
        sprite_dora[5] = new Image("C:\\Users\\Admin\\IdeaProjects\\project-Nh\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora6.png");

        final int[] index = {0};

        // tao TIMELINE CHO ANIMATION dora
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.13), e -> {
                    if (index[0] > 5) {
                        index[0] = 0;
                    } else {
                        index[0] = (index[0] + 1) % sprite_dora.length;
                    }

                    dora_run.setImage(sprite_dora[index[0]]);
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play(); // Start animation
    }

}

















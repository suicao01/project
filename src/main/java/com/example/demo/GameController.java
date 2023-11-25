package com.example.demo;


import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Set;

public class GameController extends Controller implements Initializable {
    public Label timer;
    TranslateTransition trans = new TranslateTransition();

    public Label question;
    public Button choice1;
    public Button choice4;
    public Button choice2;
    public Button choice3;

    public Label number;

    @FXML
    private Label score_label;

    //============ GAME ===========
    @FXML
    private ImageView dora_run;

    @FXML
    private AnchorPane pane_game;
    //=================================


    //----------------score bar--------------
    @FXML
    private ProgressBar score_bar;

    //-----------------------------------------


    static String word;
    static String mean;
    ArrayList<String> choice = new ArrayList<>();
    ArrayList<String> temp = new ArrayList<>();

    public Button[] buttons;
    static int score = 0;
    private int switchCount = 1;

    //lay random tu trong mang
    private String getRandomWord() throws IOException {
        Set<String> keys = getManage().getWordList().keySet();
        ArrayList<String> list = new ArrayList<>(keys);
        Collections.shuffle(list);
        return list.get(0);
    }

    //hien diem so
    public void ScoreResult(ActionEvent event) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Quiz Score");
            alert.setHeaderText(null);
            alert.setContentText("Your score: " + score + "/10");
            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    switchToMenuScene(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
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
                    score_label.setText("SCORE: "+ score +"/10");
                    check_Position_Dora(score);
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
                        if (switchCount <= 10) {
                            number.setText("Câu " + switchCount);
                            setQuestion();
                            setAnswer();
                        } else {
                            ScoreResult(event);
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
            choice.add(translator.translate("en", "vi", temp.get(i)));

        }
        mean = choice.get(0);

        Collections.shuffle(choice);

        for (int i = 0; i < choice.size(); i++) {
            buttons[i].setText(choice.get(i));
        }

    }


    public void check_Position_Dora(int score) {

/*
        // 42 width dora /2
        if (score == 1) {
            score_bar.setProgress(0.1);
            trans.setByX(46);// 88 - 42
        } else if (score == 2) {
            score_bar.setProgress(0.2);
            trans.setByX(70); // 88 + 70 - 42*2 116 -42
        } else if (score == 3) {
            score_bar.setProgress(0.3);
            trans.setByX(70); // 228 - 42 - 42 - 42 - 42
        } else if (score == 4) {
            score_bar.setProgress(0.4);
            trans.setByX(298 - 42*5);
        } else if (score == 5) {
            score_bar.setProgress(0.5);
            trans.setByX(368 - 42);
        }
*/

        if(score <= 10) {
            for (int i = 1; i <=10;i++)
            {
                if (score == i) {
                    score_bar.setProgress(0.1*i);
                    if (i == 1) { trans.setByX(46); }
                    else {trans.setByX(70);}
                }
            }
        }


//        score_bar.setProgress(0.1*10);
//        //trans.setByX(42 + 70*2);
//        trans.setByX(42 + 70*9);
        trans.play();
    }

    public void setTimer() {

        ObjectProperty<java.time.Duration> remainingDuration
                = new SimpleObjectProperty<>(java.time.Duration.ofSeconds(180));

        // time format (hh:mm:ss)
        timer.textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Time: %02d:%02d:%02d",
                                remainingDuration.get().toHours(),
                                remainingDuration.get().toMinutesPart(),
                                remainingDuration.get().toSecondsPart()),
                remainingDuration));

        // lower remaining duration every second:
        Timeline countDownTimeLine = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) ->
                remainingDuration.setValue(remainingDuration.get().minus(1, ChronoUnit.SECONDS))));

        // Set number of cycles (remaining duration in seconds):
        countDownTimeLine.setCycleCount((int) remainingDuration.get().getSeconds());

        // Show alert when time is up
        countDownTimeLine.setOnFinished(this::ScoreResult);


        // Start the time line
        countDownTimeLine.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //---------------score bar -------------------------

        // doi mau thanh score
        score_bar.setStyle("-fx-accent:#0affee");
        score_bar.setProgress(0);
    /* khi next 1 cau dung thi count_score tang len 0.1 = 10/100 thanh score
    double count_score; =>> k bi 0.9999 ma la 1 : BigDecimal count_score
   if(count_score < 1) {
    count_score += 0.1;
    score_bar.setProgress(count_score); thi thanh se tang


    // lay ra diem 10,20,30,...
    Label.setText(Integer.toString((int)Math.round(count_score*100)))
    ]

     */
        //--------------------------------------------------


        //----------------dora animation -------------------------------
        // Load sprite image
        Image[] sprite_dora = new Image[6];


        buttons = new Button[]{choice1, choice2, choice3, choice4};
        try {
            setTimer();
            setQuestion();
            setAnswer();
            switchQuestion();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }





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

        //---------------------------------------------------------------------


        // move dora


        trans.setNode(dora_run);
        trans.setDuration(Duration.seconds(1));


        trans.play();
    }


}
















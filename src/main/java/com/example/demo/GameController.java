package com.example.demo;


import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class GameController extends Controller implements Initializable {
    public Label timer;

    public Button SoundOn;
    public ImageView symbol;

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


     String word;
     String mean;
    ArrayList<String> choice = new ArrayList<>();
    ArrayList<String> temp = new ArrayList<>();

    public Button[] buttons;
     int score = 0;
     int switchCount = 1;

    @FXML
    private TextFlow question_flow;
    //lay random tu trong mang
    private String getRandomWord() throws IOException {
        RetrieveSecondLineAfterAt retrieve = new RetrieveSecondLineAfterAt();
        Set<String> keys = retrieve.insertFile().keySet();
        ArrayList<String> list = new ArrayList<>(keys);
        Collections.shuffle(list);
        return list.get(0);
    }

    //hien diem so
    public void ScoreResult(ActionEvent event) throws IOException {
        String scoreString = Integer.toString(score);

        // Write score to file
        Path filePath = Path.of("score.txt");
        Files.write(filePath, scoreString.getBytes(), StandardOpenOption.CREATE);
       if (score == 10) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("win.fxml")));
           stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
       }
       else {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fail.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
       }
    }

    public void playSong() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("Caliginous-Hearthfire.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);

        Image imageOn = new Image("file:E:\\GITHUB\\project\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\volume_on.png");
        Image imageOff = new Image("file:E:\\GITHUB\\project\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\volume_off.png");

        symbol.setImage(imageOn);
        SoundOn.setGraphic(symbol);

        BooleanProperty isSoundOn = new SimpleBooleanProperty(true);

        SoundOn.setOnAction(event -> {
            if (isSoundOn.get()) {
                symbol.setImage(imageOff);
                clip.stop();
            } else {
                symbol.setImage(imageOn);
                clip.start();
            }
            isSoundOn.set(!isSoundOn.get());
        });

        clip.start();


    }
    //hien cau hoi
    public void setQuestion() throws IOException {
        for (int i = 0; i < 4; i++) {
            temp.add(getRandomWord());
        }
        word = temp.get(0);
        question_flow.getChildren().clear();
        Text text_word = new Text(word);
        text_word.setStyle("-fx-fill: red;-fx-font-weight: bold;-fx-font-size: 20;");

        Text text_ques = new Text(" có nghĩa nào trong các đáp án sau:");
        text_ques.setStyle("-fx-fill: black; -fx-font-weight: bold;-fx-font-size: 20;");


        question_flow.getChildren().addAll(text_word, text_ques);
        //question.setText(word + );

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

                buttons[i].setStyle("-fx-background-color: #0eff00;");

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
       RetrieveSecondLineAfterAt retrieve  = new RetrieveSecondLineAfterAt();
        for (int i = 0; i < temp.size(); i++) {
            choice.add(retrieve.dictionaryLookup(temp.get(i)));

        }

        mean = choice.get(0);

        Collections.shuffle(choice);

        for (int i = 0; i < choice.size(); i++) {
            buttons[i].setText(choice.get(i));
        }

    }


    public void check_Position_Dora(int score) {


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



        trans.play();
    }

    public void setTimer() {
        ObjectProperty<java.time.Duration> remainingDuration
                = new SimpleObjectProperty<>(java.time.Duration.ofSeconds(60));


        timer.textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Time: %02d:%02d:%02d",
                                remainingDuration.get().toHours(),
                                remainingDuration.get().toMinutesPart(),
                                remainingDuration.get().toSecondsPart()),
                remainingDuration));


        Timeline countDownTimeLine = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) ->
                remainingDuration.setValue(remainingDuration.get().minus(1, ChronoUnit.SECONDS))));


        countDownTimeLine.setCycleCount((int) remainingDuration.get().getSeconds());


        countDownTimeLine.setOnFinished(event -> {
            try {
                if(score == 10) {
                    switchToMWinScene(timer);
                }
                else switchToFailScene(timer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });




        countDownTimeLine.play();
    }


    public void switchToMWinScene(Node sourceNode) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("win.fxml")));
         stage = (Stage) sourceNode.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToFailScene(Node sourceNode) throws IOException {
       root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fail.fxml")));
         stage = (Stage) sourceNode.getScene().getWindow();
         scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //---------------score bar -------------------------

        // doi mau thanh score
        score_bar.setStyle("-fx-accent:#0affee");
        score_bar.setProgress(0);


        //----------------dora animation -------------------------------
        // Load sprite image
        Image[] sprite_dora = new Image[6];


        buttons = new Button[]{choice1, choice2, choice3, choice4};
        try {
            setQuestion();
            setAnswer();
            setTimer();
            switchQuestion();
            playSong();

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }


        sprite_dora[0] = new Image("file:E:\\GITHUB\\project\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora1.png");
        sprite_dora[1] = new Image("file:E:\\GITHUB\\project\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora2.png");
        sprite_dora[2] = new Image("file:E:\\GITHUB\\project\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora3.png");
        sprite_dora[3] = new Image("file:E:\\GITHUB\\project\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora4.png");
        sprite_dora[4] = new Image("file:E:\\GITHUB\\project\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora5.png");
        sprite_dora[5] = new Image("file:E:\\GITHUB\\project\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora6.png");



//        sprite_dora[0] = new Image("C:\\Users\\Admin\\IdeaProjects\\project-Nh - Copy\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora1.png");
//        sprite_dora[1] = new Image("C:\\Users\\Admin\\IdeaProjects\\project-Nh - Copy\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora2.png");
//        sprite_dora[2] = new Image("C:\\Users\\Admin\\IdeaProjects\\project-Nh - Copy\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora3.png");
//        sprite_dora[3] = new Image("C:\\Users\\Admin\\IdeaProjects\\project-Nh - Copy\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora4.png");
//        sprite_dora[4] = new Image("C:\\Users\\Admin\\IdeaProjects\\project-Nh - Copy\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora5.png");
//        sprite_dora[5] = new Image("C:\\Users\\Admin\\IdeaProjects\\project-Nh - Copy\\src\\main\\resources\\com\\example\\demo\\backgroundGame\\dora\\dora6.png");


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















